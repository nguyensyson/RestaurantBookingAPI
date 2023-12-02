package com.poly.bookingapi.service;

import com.poly.bookingapi.dto.CategoryViewDTO;

import java.util.List;

public interface CategoryProductService {

    List<CategoryViewDTO> getAll();
    CategoryViewDTO getByID(Integer id);
    String addCategory(CategoryViewDTO dto);
    String updateCategory(CategoryViewDTO dto, Integer id);
}
