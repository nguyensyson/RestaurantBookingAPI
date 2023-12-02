package com.poly.bookingapi.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @Column(name = "user_name")
    private String username;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "sdt")
    private String sdt;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "created_at")
    private LocalDate createAt;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "update_at")
    private LocalDate updateAt;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<ProductEvaluate> listProductEvaluate;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<Reservation> listReservation;
}
