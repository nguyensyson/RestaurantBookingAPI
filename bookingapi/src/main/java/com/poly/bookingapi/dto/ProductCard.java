package com.poly.bookingapi.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductCard {
    private Integer id;
    private String imageThumbnail;
    private String name;
    private Integer rating;
    private Integer discount;
    private Long price;
}
