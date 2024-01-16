package com.poly.bookingapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poly.bookingapi.entity.CategoryProduct;
import com.poly.bookingapi.entity.ComboDetail;
import com.poly.bookingapi.entity.ImageProduct;
import com.poly.bookingapi.entity.Product;
import com.poly.bookingapi.proxydto.ProductProxy;
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
    private String avatar;
    private String name;
    private Long price;
    private String introduce;
    private List<ProductProxy> listItem;
    private Integer status;
}
