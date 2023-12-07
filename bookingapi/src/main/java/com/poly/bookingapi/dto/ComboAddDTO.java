package com.poly.bookingapi.dto;

import com.poly.bookingapi.entity.CategoryProduct;
import com.poly.bookingapi.entity.ComboDetail;
import com.poly.bookingapi.entity.ImageProduct;
import com.poly.bookingapi.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComboAddDTO {

    private MultipartFile avatar;
//    private List<ImageProduct> images;
    private String name;
    private String introduce;
    private List<Product> listItem;
}
