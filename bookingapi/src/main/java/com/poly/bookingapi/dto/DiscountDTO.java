package com.poly.bookingapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDTO {

    private String nameDiscount;
    private Integer discountValue;
    private Integer status;
    private String introduce;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Integer> listProduct;
    
}
