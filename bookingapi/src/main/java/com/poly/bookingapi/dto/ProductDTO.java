package com.poly.bookingapi.dto;

import com.poly.bookingapi.entity.Discount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Integer id;
    private String nameProduct;
    private Long price;
    private Integer quantity;
    private Discount discount;
    private Long newPrice;
}
