 package com.poly.bookingapi.controller;

import com.poly.bookingapi.dto.CategoryViewDTO;
import com.poly.bookingapi.service.CategoryProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryProductController {

    @Autowired
    CategoryProductService categoryService;

    @GetMapping("api/view/category-product/get-all")
    public ResponseEntity<List<CategoryViewDTO>> getALl() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("api/view/category-product/{id}")
    public ResponseEntity<CategoryViewDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(categoryService.getByID(id));
    }

    @PostMapping("api/admin/category-product/add")
    public ResponseEntity<String> addCategory(@RequestBody CategoryViewDTO dto) {
        return ResponseEntity.ok(categoryService.addCategory(dto));
    }

    @PutMapping("api/admin/category-product/update/{id}")
    public ResponseEntity<String> updateCategory(@RequestBody CategoryViewDTO dto, @PathVariable Integer id) {
        return ResponseEntity.ok(categoryService.updateCategory(dto, id));
    }

}
