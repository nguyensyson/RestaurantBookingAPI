package com.poly.bookingapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "combo_detail")
public class ComboDetail {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_combo", referencedColumnName = "id")
    private Product combo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_item", referencedColumnName = "id")
    private Product item;

    @Column(name = "name_product")
    private String nameProduct;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "created_at")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate createdAt;

    @Column(name = "update_at")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate updateAt;
}
