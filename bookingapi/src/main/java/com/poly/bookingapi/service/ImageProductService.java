package com.poly.bookingapi.service;

import com.poly.bookingapi.dto.ImageProductDTO;
import com.poly.bookingapi.entity.ImageProduct;

import java.util.List;

public interface ImageProductService {

    List<ImageProductDTO> getListImageProduct(Integer pageNo);

    ImageProduct add(ImageProductDTO imageProductDTO, String urlImage);

}
