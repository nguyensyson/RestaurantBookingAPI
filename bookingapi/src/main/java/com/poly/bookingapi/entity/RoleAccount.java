package com.poly.bookingapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
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
}
