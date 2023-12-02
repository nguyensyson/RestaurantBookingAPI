package com.poly.bookingapi.service.impl;

import com.poly.bookingapi.dto.CategoryViewDTO;
import com.poly.bookingapi.entity.CategoryProduct;
import com.poly.bookingapi.repository.CategoryProductRepository;
import com.poly.bookingapi.service.CategoryProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryProductServiceImpl implements CategoryProductService{

    @Autowired
    CategoryProductRepository categoryProductRepository;

    @Override
    public List<CategoryViewDTO> getAll() {
        List<CategoryProduct> categoryProducts = categoryProductRepository.findAll();
        List<CategoryViewDTO> viewDTOS = new ArrayList<>();
        for (CategoryProduct c: categoryProducts) {
            CategoryViewDTO dto = new CategoryViewDTO();
            dto.setId(c.getId());
            dto.setNameCategory(c.getNameCategory());
            dto.setListProduct(c.getListProduct());
            viewDTOS.add(dto);
        }
        return viewDTOS;
    }

    @Override
    public CategoryViewDTO getByID(Integer id) {
        Optional<CategoryProduct> categoryProducts = categoryProductRepository.findById(id);
            CategoryViewDTO dto = new CategoryViewDTO();
            dto.setId(categoryProducts.get().getId());
            dto.setNameCategory(categoryProducts.get().getNameCategory());
            dto.setListProduct(categoryProducts.get().getListProduct());
        return dto;
    }

    @Override
    public String addCategory(CategoryViewDTO dto) {
        CategoryProduct categoryProduct = new CategoryProduct();
        categoryProduct.setNameCategory(dto.getNameCategory());
        categoryProduct.setCreateAt(LocalDate.now());
        categoryProduct.setUpdateAt(LocalDate.now());
        categoryProductRepository.save(categoryProduct);
        return "Add thành công";
    }

    @Override
    public String updateCategory(CategoryViewDTO dto, Integer id) {
        Optional<CategoryProduct> categoryProduct = categoryProductRepository.findById(id);
        categoryProduct.get().setNameCategory(dto.getNameCategory());
        categoryProduct.get().setUpdateAt(LocalDate.now());
        categoryProductRepository.save(categoryProduct.get());
        return "update thành công";
    }
}
