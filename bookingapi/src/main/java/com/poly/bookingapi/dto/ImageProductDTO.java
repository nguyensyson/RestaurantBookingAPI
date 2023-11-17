package com.poly.bookingapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poly.bookingapi.entity.ImageProduct;
import com.poly.bookingapi.entity.Product;
import com.poly.bookingapi.repository.ImageProductRepository;
import com.poly.bookingapi.repository.ProductRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageProductDTO {

    private Integer id;

    private String images;

    private Integer product;

    private LocalDate createdAt;

    private LocalDate updateAt;


}
