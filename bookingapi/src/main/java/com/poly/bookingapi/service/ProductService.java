package com.poly.bookingapi.service;

import com.poly.bookingapi.dto.*;
import org.springframework.data.domain.Page;

public interface ProductService {

    Page<ProductViewDTO> getAllNotCombo(ProductSearchRequest request);

    ProductViewDTO getById(Integer id);

    ComboViewDTO getComboById(Integer id);

    Page<ComboViewDTO> searchCombo(ProductComboSearchRequest model);

    String addProduct(ProductAddDTO dto);

    String updateProduct(ProductAddDTO dto, Integer id);

    String addCombo(ComboAddDTO dto);

    String updateCombo(ComboAddDTO dto, Integer id);

    Page<ProductCard> findBySearch(ProductSearchRequest request);
}
