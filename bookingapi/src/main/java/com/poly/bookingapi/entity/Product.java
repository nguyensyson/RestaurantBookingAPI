package com.poly.bookingapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private CategoryProduct category;
    @Column(name = "avatar_product")
    private String avatar;
    @Column(name = "name_product")
    private String nameProduct;
    @Column(name = "price")
    private BigDecimal price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id", referencedColumnName = "id")
    private Discount discount;
    @Column(name = "introduce")
    private String introduce;
    @Column(name = "status")
    private Integer status;
    @Column(name = "created_at")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate createdAt;
    @Column(name = "update_at")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate updateAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", referencedColumnName = "id")
    private Admin updatedBy;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ImageProduct> listImage;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductEvaluate> listEvaluate;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ReservationDishDetail> listReservationDishDetail;
}
