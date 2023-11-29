package com.poly.bookingapi.service.impl;

import com.poly.bookingapi.dto.AccountDTO;
import com.poly.bookingapi.entity.Account;
import com.poly.bookingapi.entity.Admin;
import com.poly.bookingapi.entity.Client;
import com.poly.bookingapi.entity.RoleAccount;
import com.poly.bookingapi.repository.AccountRepository;
import com.poly.bookingapi.repository.AdminRepository;
import com.poly.bookingapi.repository.ClientRepository;
import com.poly.bookingapi.repository.RoleAccountRepository;
import com.poly.bookingapi.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    RoleAccountRepository roleAccountRepository;

    private final PasswordEncoder passwordEncoder;
    public AccountServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String createAccount(AccountDTO dto) {

        Account account = new Account();
        account.setUsername(dto.getSdt());
        account.setPassword(passwordEncoder.encode(dto.getPassword()));
        account.setRole(dto.getRole());
        account.setStatus(1);
        account.setCreateAt(LocalDate.now());
        account.setUpdateAt(LocalDate.now());
        Account accountSave = accountRepository.save(account);

        if(dto.getRole().getId() == 3) {
            Client client = new Client();
            client.setAccount(accountSave);
            client.setSdt(dto.getSdt());
            client.setUpdateAt(LocalDate.now());
            client.setFullname(dto.getFullname());
            client.setCreateAt(LocalDate.now());
            client.setUsername(dto.getSdt());
            Client clientSave = clientRepository.save(client);
            return "Tạo mới thành công";
        }

        Admin admin = new Admin();
        admin.setAccount(accountSave);
        admin.setSdt(dto.getSdt());
        admin.setUpdateAt(LocalDate.now());
        admin.setFullName(dto.getFullname());
        admin.setCreatedAt(LocalDate.now());
        admin.setUserName(dto.getSdt());
        Admin adminSave = adminRepository.save(admin);
        return "Tạo mới thành công";
    }
}
