package com.poly.bookingapi.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
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
    private Long price;

    @Column(name = "new_price")
    private Long newPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id", referencedColumnName = "id")
    private Discount discount;

    @Column(name = "introduce")
    private String introduce;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private ProductStatus status;

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
    private List<ReservationProduct> listReservationProduct;

    @OneToMany(mappedBy = "combo", fetch = FetchType.LAZY)
    private List<ComboDetail> listCombo;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<ComboDetail> listitem;
}
