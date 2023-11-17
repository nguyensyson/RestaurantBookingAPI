package com.poly.bookingapi.entity;

import com.poly.bookingapi.dto.ImageProductDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "images_product")
public class ImageProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "images")
    private String images;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(name = "created_at")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate createdAt;

    @Column(name = "update_at")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate updateAt;

    public ImageProductDTO loadDataView() {
        ImageProductDTO imageProductDTO = new ImageProductDTO();
        imageProductDTO.setId(id);
        imageProductDTO.setImages(images);
        imageProductDTO.setProduct(product);
        imageProductDTO.setCreatedAt(createdAt);
        imageProductDTO.setUpdateAt(updateAt);
        return imageProductDTO;
    }
}
