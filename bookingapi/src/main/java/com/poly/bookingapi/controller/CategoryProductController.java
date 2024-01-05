 package com.poly.bookingapi.controller;

import com.poly.bookingapi.dto.CategoryAddDTO;
import com.poly.bookingapi.dto.CategoryProductRequest;
import com.poly.bookingapi.service.CategoryProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryProductController {

    @Autowired
    CategoryProductService categoryService;

    @PostMapping("api/view/category-product/get-all")
    public ResponseEntity<Page<CategoryAddDTO>> getALl(@RequestBody CategoryProductRequest request) {
        return ResponseEntity.ok(categoryService.getAll(request));
    }

    @GetMapping("api/view/category-product/{id}")
    public ResponseEntity<CategoryAddDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(categoryService.getByID(id));
    }

    @PostMapping("api/admin/category-product/add")
    public ResponseEntity<String> addCategory(@RequestBody CategoryAddDTO dto) {
        return ResponseEntity.ok(categoryService.addCategory(dto));
    }

    @PutMapping("api/admin/category-product/update/{id}")
    public ResponseEntity<String> updateCategory(@RequestBody CategoryAddDTO dto, @PathVariable Integer id) {
        return ResponseEntity.ok(categoryService.updateCategory(dto, id));
    }

}
