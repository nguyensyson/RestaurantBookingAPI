package com.poly.bookingapi.entity;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Transactional
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "role_account")
public class RoleAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name_role")
    private String nameRole;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "created_at")
    private LocalDate createdAt;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "update_at")
    private LocalDate updateAt;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<Account> listAccount;

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + nameRole));
        return authorities;
    }
}
