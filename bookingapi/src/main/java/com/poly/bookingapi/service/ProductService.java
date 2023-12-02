package com.poly.bookingapi.service;

import com.poly.bookingapi.dto.ComboAddDTO;
import com.poly.bookingapi.dto.ComboViewDTO;
import com.poly.bookingapi.dto.ProductAddDTO;
import com.poly.bookingapi.dto.ProductViewDTO;

import java.util.List;

public interface ProductService {

    List<ProductViewDTO> getAllNotCombo();
    List<ProductViewDTO> getByCategory(Integer id);
    List<ProductViewDTO> search(String name);
    ProductViewDTO getById(Integer id);
    ComboViewDTO getComboById(Integer id);
    List<ComboViewDTO> getAllCombo();
    List<ComboViewDTO> searchCombo(String name);
    String addProduct(ProductAddDTO dto);
    String updateProduct(ProductAddDTO dto, Integer id);
    String addCombo(ComboAddDTO dto);
    String updateCombo(ComboAddDTO dto, Integer id);

}
