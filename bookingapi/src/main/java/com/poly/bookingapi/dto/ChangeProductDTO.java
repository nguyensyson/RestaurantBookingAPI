package com.poly.bookingapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChangeProductDTO {

    private Long originalPrice;
    private Long actualPrice;
    private Long priceToPay;
    private List<ProductDTO> listPorduct;
}
