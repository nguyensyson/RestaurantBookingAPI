package com.poly.bookingapi.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "account")
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_name")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "status")
    private Integer status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private RoleAccount role;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "created_at")
    private LocalDate createAt;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "update_at")
    private LocalDate updateAt;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Client> listClient;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Admin> listManager;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Token> listToken;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(role != null) {
            Hibernate.initialize(role);
        }
        return this.role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static class Builder {

        private Integer id;
        private String username;
        private String password;
        private String avatar;
        private Integer status;
        private RoleAccount role;
        private String resetPasswordToken;
        private LocalDate createAt;
        private LocalDate updateAt;

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setAvatar(String avatar) {
            this.avatar = avatar;
            return this;
        }

        public Builder setStatus(Integer status) {
            this.status = status;
            return this;
        }

        public Builder setRole(RoleAccount role) {
            this.role = role;
            return this;
        }

        public Builder setResetPasswordToken(String resetPasswordToken) {
            this.resetPasswordToken = resetPasswordToken;
            return this;
        }

        public Builder setCreateAt(LocalDate createAt) {
            this.createAt = createAt;
            return this;
        }

        public Builder setUpdateAt(LocalDate updateAt) {
            this.updateAt = updateAt;
            return this;
        }

        public Account build() {
            Account account = new Account();
            account.id = this.id;
            account.username = this.username;
            account.password = this.password;
            account.avatar = this.avatar;
            account.status = this.status;
            account.role = this.role;
            account.resetPasswordToken = this.resetPasswordToken;
            account.createAt = this.createAt;
            account.updateAt = this.updateAt;
            return account;
        }
    }
}
