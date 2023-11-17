package com.poly.bookingapi.service.Impl;

import com.cloudinary.Cloudinary;
import com.poly.bookingapi.dto.ImageProductDTO;
import com.poly.bookingapi.entity.ImageProduct;
import com.poly.bookingapi.repository.ImageProductRepository;
import com.poly.bookingapi.service.ImageProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageProductServiceImpl implements ImageProductService {
    @Autowired
    ImageProductRepository imageProductRepository;

    @Autowired
    private Cloudinary cloudinaryConfig;

    @Override
    public List<ImageProductDTO> getListImageProduct(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 16);
        Page<ImageProduct> images = imageProductRepository.findAll(pageable);
        List<ImageProductDTO> imageProductDTOS = new ArrayList<>();
        for (ImageProduct i : images.getContent()) {
            ImageProductDTO dto = i.loadDataView();
            imageProductDTOS.add(dto);
        }
        return  imageProductDTOS;
    }


    @Override
    public ImageProduct add(ImageProductDTO imageProductDTO, String urlImage) {
        ImageProduct imageProduct = ImageProduct.builder()
                .images(urlImage)
                .product(imageProductDTO.getProduct())
                .createdAt(imageProductDTO.getCreatedAt())
                .updateAt(imageProductDTO.getUpdateAt()).build();
        return imageProductRepository.save(imageProduct);
    }
}
