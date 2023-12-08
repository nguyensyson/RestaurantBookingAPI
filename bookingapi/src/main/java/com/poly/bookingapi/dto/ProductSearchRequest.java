package com.poly.bookingapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ProductSearchRequest extends PageModel {
    private String keyword;
    private List<Integer> categoryIdList;
    private String sortBy;
}
