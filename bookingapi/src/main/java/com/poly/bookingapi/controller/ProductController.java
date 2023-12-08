package com.poly.bookingapi.controller;

import com.poly.bookingapi.dto.ComboViewDTO;
import com.poly.bookingapi.dto.ProductViewDTO;
import com.poly.bookingapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/view/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/get-all")
    public ResponseEntity<Page<ProductViewDTO>> getALL() {
        return ResponseEntity.ok(productService.getAllNotCombo());
    }

    @GetMapping("/get-by-category/{id}")
    public ResponseEntity<Page<ProductViewDTO>> getByCategory(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.getByCategory(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductViewDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @GetMapping("combo/{id}")
    public ResponseEntity<ComboViewDTO> getComboById(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.getComboById(id));
    }

    @GetMapping("/combo/get")
    public ResponseEntity<Page<ComboViewDTO>> getCombo() {
        return ResponseEntity.ok(productService.getAllCombo());
    }

    @GetMapping("/combo/search")
    public ResponseEntity<Page<ComboViewDTO>> searchCombo(@RequestBody String text) {
        return ResponseEntity.ok(productService.searchCombo(text));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductViewDTO>> search(@RequestBody String text) {
        return ResponseEntity.ok(productService.search(text));
    }

}
