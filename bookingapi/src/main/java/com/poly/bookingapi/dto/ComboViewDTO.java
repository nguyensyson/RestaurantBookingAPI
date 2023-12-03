package com.poly.bookingapi.dto;

import com.poly.bookingapi.entity.CategoryProduct;
import com.poly.bookingapi.entity.ComboDetail;
import com.poly.bookingapi.entity.ImageProduct;
import com.poly.bookingapi.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComboViewDTO {

    private Integer id;
    private CategoryProduct category;
    private String avatar;
    private List<ImageProduct> images;
    private String name;
    private Long price;
    private Integer discount;
    private String introduce;
    private Integer status;
    private List<ComboDetail> listItem;
}
