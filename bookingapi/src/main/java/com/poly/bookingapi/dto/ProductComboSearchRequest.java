package com.poly.bookingapi.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductComboSearchRequest extends PageModel{
    private String name;
}
