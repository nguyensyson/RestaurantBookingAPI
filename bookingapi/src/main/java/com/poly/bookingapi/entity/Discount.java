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
@Table(name = "discount")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name_discount")
    private String nameDiscount;

    @Column(name = "discount_value")
    private Integer discountValue;

    @Column(name = "status")
    private Integer status;

    @Column(name = "introduce")
    private String introduce;

    @Column(name = "start_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate startDate;

    @Column(name = "end_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate endDate;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "created_at")
    private LocalDate createAt;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "update_at")
    private LocalDate updateAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", referencedColumnName = "id")
    private Admin updatedBy;

    @OneToMany(mappedBy = "discount", fetch = FetchType.LAZY)
    private List<Product> listProduct;
}
