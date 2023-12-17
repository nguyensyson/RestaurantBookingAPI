package com.poly.bookingapi.dto;

import com.poly.bookingapi.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryViewDTO {

    private Integer id;
    private MultipartFile image;
    private String nameCategory;
}
