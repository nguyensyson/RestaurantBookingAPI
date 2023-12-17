package com.poly.bookingapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poly.bookingapi.entity.CategoryProduct;
import com.poly.bookingapi.entity.ImageProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductViewDTO {

    private Integer id;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private CategoryProduct category;
    private String avatar;
//    private List<ImageProduct> images;
    private String name;
    private Long price;
    private Integer discount;
    private String introduce;
    private Integer status;
}
