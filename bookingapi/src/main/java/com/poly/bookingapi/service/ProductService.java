package com.poly.bookingapi.service;

import com.poly.bookingapi.dto.ComboAddDTO;
import com.poly.bookingapi.dto.ComboViewDTO;
import com.poly.bookingapi.dto.ProductAddDTO;
import com.poly.bookingapi.dto.ProductViewDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    Page<ProductViewDTO> getAllNotCombo();
    Page<ProductViewDTO> getByCategory(Integer id);
    Page<ProductViewDTO> search(String name);
    ProductViewDTO getById(Integer id);
    ComboViewDTO getComboById(Integer id);
    Page<ComboViewDTO> getAllCombo();
    Page<ComboViewDTO> searchCombo(String name);
    String addProduct(ProductAddDTO dto);
    String updateProduct(ProductAddDTO dto, Integer id);
    String addCombo(ComboAddDTO dto);
    String updateCombo(ComboAddDTO dto, Integer id);

}
