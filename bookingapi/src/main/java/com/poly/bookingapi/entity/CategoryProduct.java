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
@Builder
@Entity
@Table(name = "category_product")
public class CategoryProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "name_category")
    private String nameCategory;

    @Column(name = "introduce")
    private String introduce;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "created_at")
    private LocalDate createAt;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "update_at")
    private LocalDate updateAt;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Product> listProduct;
}
