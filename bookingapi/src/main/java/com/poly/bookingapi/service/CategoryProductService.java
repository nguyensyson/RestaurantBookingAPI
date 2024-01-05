package com.poly.bookingapi.service;

import com.poly.bookingapi.dto.CategoryAddDTO;
import com.poly.bookingapi.dto.CategoryProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryProductService {

    Page<CategoryAddDTO> getAll(CategoryProductRequest request);
    CategoryAddDTO getByID(Integer id);
    String addCategory(CategoryAddDTO dto);
    String updateCategory(CategoryAddDTO dto, Integer id);
}
