package com.poly.bookingapi.dto;

import com.poly.bookingapi.proxydto.ProductProxy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountViewDTO {
    private String nameDiscount;
    private Integer discountValue;
    private Integer status;
    private String introduce;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<ProductProxy> listProduct;
}
