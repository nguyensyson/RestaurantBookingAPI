package com.poly.bookingapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "sdt")
    private String sdt;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "update_at")
    private LocalDate updateAt;

    @OneToMany(mappedBy = "updatedBy", fetch = FetchType.LAZY)
    private List<Product> listProduct;

    @OneToMany(mappedBy = "updatedBy", fetch = FetchType.LAZY)
    private List<Discount> listDiscount;

    @OneToMany(mappedBy = "updatedBy", fetch = FetchType.LAZY)
    private List<Reservation> listReservation;
}
