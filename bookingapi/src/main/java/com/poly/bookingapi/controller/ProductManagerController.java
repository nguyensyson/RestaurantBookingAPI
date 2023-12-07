package com.poly.bookingapi.controller;

import com.poly.bookingapi.dto.ComboAddDTO;
import com.poly.bookingapi.dto.ProductAddDTO;
import com.poly.bookingapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin/product")
public class ProductManagerController {

    @Autowired
    ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@ModelAttribute ProductAddDTO dto) {
        return ResponseEntity.ok(productService.addProduct(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProduct(@ModelAttribute ProductAddDTO dto, @PathVariable Integer id) {
        return ResponseEntity.ok(productService.updateProduct(dto, id));
    }

    @PostMapping("/combo/add")
    public ResponseEntity<String> addComboProduct(@ModelAttribute ComboAddDTO dto) {
        return ResponseEntity.ok(productService.addCombo(dto));
    }

    @PutMapping("/combo/update/{id}")
    public ResponseEntity<String> updateComboProduct(@ModelAttribute ComboAddDTO dto, @PathVariable Integer id) {
        return ResponseEntity.ok(productService.updateCombo(dto, id));
    }
}