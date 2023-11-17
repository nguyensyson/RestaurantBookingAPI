package com.poly.bookingapi.dto;

import com.poly.bookingapi.entity.ImageProduct;
import com.poly.bookingapi.entity.Product;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageProductDTO {

    private Integer id;

    private String images;

    private Product product;

    private LocalDate createdAt;

    private LocalDate updateAt;

    public ImageProduct loadDataView() {
        ImageProduct imageProduct = new ImageProduct();
        imageProduct.setImages(images);
        imageProduct.setProduct(product);
        imageProduct.setCreatedAt(createdAt);
        imageProduct.setUpdateAt(createdAt);
        imageProduct.setUpdateAt(updateAt);
        return imageProduct;
    }

    public ImageProduct loadDataView(ImageProduct imageProduct)  {
        imageProduct.setImages(images);
        imageProduct.setProduct(product);
        imageProduct.setCreatedAt(createdAt);
        imageProduct.setUpdateAt(createdAt);
        imageProduct.setUpdateAt(updateAt);
        return imageProduct;
    }

}
