package com.poly.bookingapi.dto;

import com.poly.bookingapi.entity.CategoryProduct;
import com.poly.bookingapi.entity.ImageProduct;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ProductViewDTO {

    @Id
    private Integer id;
    @ManyToOne
    private CategoryProduct category;
    private String avatar;
    @OneToMany
    private List<ImageProduct> images;
    private String name;
    private Long price;
    private Double discount;
    private String introduce;
    private Integer status;

}
