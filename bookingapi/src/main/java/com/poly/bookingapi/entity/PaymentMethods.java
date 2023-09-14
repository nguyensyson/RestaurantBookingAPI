package com.poly.bookingapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "payment_methods")
public class PaymentMethods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "created_at")
    private LocalDate createdAt;
    @Column(name = "update_at")
    private LocalDate updateAt;
    @OneToMany(mappedBy = "paymentMethods", fetch = FetchType.LAZY)
    private List<PaymentDetail> listPaymentDetail;
}
