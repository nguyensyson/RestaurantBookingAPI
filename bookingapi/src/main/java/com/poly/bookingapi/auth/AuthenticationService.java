package com.poly.bookingapi.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.bookingapi.config.JwtService;
import com.poly.bookingapi.dto.TokenType;
import com.poly.bookingapi.entity.*;
import com.poly.bookingapi.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;

@Service
public class AuthenticationService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private RoleAccountRepository roleAccountRepository;

    private final AccountRepository accountRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(AccountRepository accountRepository, TokenRepository tokenRepository,
                                 PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.accountRepository = accountRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public void changePassword(String username, String currentPassword, String newPassword) {
        Account user = accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        if (passwordEncoder.matches(currentPassword, user.getPassword())) {
            String encodedNewPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedNewPassword);
            accountRepository.save(user);
        } else {
            throw new IllegalArgumentException("Incorrect current password");
        }
    }

    public String register(RegisterRequest request) {
        RoleAccount role = roleAccountRepository.getById(3);
        var user = new Account.Builder()
                .setUsername(request.getSdt())
                .setPassword(passwordEncoder.encode(request.getPassword()))
                .setRole(role)
                .setStatus(1)
                .setCreateAt(LocalDate.now())
                .setUpdateAt(LocalDate.now())
                .build();
        var savedUser = accountRepository.save(user);

        Client client = new Client();
        client.setAccount(savedUser);
        client.setUsername(request.getSdt());
        client.setFullname(request.getFullname());
        client.setSdt(request.getSdt());
        client.setCreateAt(LocalDate.now());
        client.setUpdateAt(LocalDate.now());
        Client saveClient = clientRepository.save(client);
//	    var jwtToken = jwtService.generateToken(user);
//	    var refreshToken = jwtService.generateRefreshToken(user);
//	    saveUserToken(savedUser, jwtToken);
//
//	    return new AuthenticationResponse.Builder()
//	        .setAccessToken(jwtToken)
//                .setRefreshToken(refreshToken)
//	        .build();

        return "Đăng ký thành công";
    }

    private void saveUserToken(Account user, String jwtToken) {
        var token = new Token.Builder()
                .setAccount(user)
                .setStoken(jwtToken)
                .setTokenType(TokenType.BEARER)
                .setExpired(false)
                .setRevoked(false)
                .build();
        tokenRepository.save(token);
    }

    public <T> AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = accountRepository.findByUsername(request.getUsername())
                .orElseThrow();

        System.out.println(user.getUsername());
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        if(user.getRole().getId() == 3) {

            return new AuthenticationResponse.Builder()
                    .setAccessToken(jwtToken)
                    .setRefreshToken(refreshToken)
                    .setRoleId(user.getRole().getId())
                    .setUserId(clientRepository.getByAccount(user.getId()).getId())
                    .build();
        } else {
            return new AuthenticationResponse.Builder()
                    .setAccessToken(jwtToken)
                    .setRefreshToken(refreshToken)
                    .setRoleId(user.getRole().getId())
                    .setUserId(adminRepository.getByAccount(user.getId()).getId())
                    .build();
        }

//        Object userData;
//        if (user.getRole().getId() == 3) {
//            userData = clientRepository.getByAccount(user.getId());
//        } else {
//            userData = adminRepository.getByAccount(user.getId());
//        }
//
//// Kiểm tra kiểu dữ liệu và ép kiểu an toàn
//        if ((userData instanceof Client) && (responseType.isAssignableFrom(Client.class))) {
//            return new AuthenticationResponse.Builder<T>()
//                    .setAccessToken(jwtToken)
//                    .setRefreshToken(refreshToken)
//                    .setRoleId(user.getRole().getId())
//                    .setUser((T) userData)
//                    .build();
//        } else if ((userData instanceof Admin) && (responseType.isAssignableFrom(Admin.class))) {
//            return new AuthenticationResponse.Builder<T>()
//                    .setAccessToken(jwtToken)
//                    .setRefreshToken(refreshToken)
//                    .setRoleId(user.getRole().getId())
//                    .setUser((T) userData)
//                    .build();
//        } else {
//            return null;
//        }
    }

    private void revokeAllUserTokens(Account user) {
        var validUserTokens = tokenRepository.findAllValidTokenByPhatTu(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.accountRepository.findByUsername(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = new AuthenticationResponse.Builder()
                        .setAccessToken(accessToken)
                        .setRefreshToken(refreshToken)
                        .build();
//                AuthenticationResponse authResponse = new AuthenticationResponse();
//                authResponse.setRefreshToken(refreshToken);
//                authResponse.setAccessToken(accessToken);
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
