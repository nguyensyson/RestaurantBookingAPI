package com.poly.bookingapi.controller;

import com.poly.bookingapi.dto.*;
import com.poly.bookingapi.proxydto.ProductProxy;
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

    @PostMapping("/get-all")
    public ResponseEntity<Page<ProductViewDTO>> getALL(@RequestBody ProductSearchRequest model) {
        return ResponseEntity.ok(productService.getAllNotCombo(model));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductViewDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @GetMapping("combo/{id}")
    public ResponseEntity<ComboViewDTO> getComboById(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.getComboById(id));
    }

    @PostMapping("/combo/search")
    public ResponseEntity<Page<ComboViewDTO>> searchCombo(@RequestBody ProductComboSearchRequest model) {
        return ResponseEntity.ok(productService.searchCombo(model));
    }

    @PostMapping("/findBySearch")
    public Page<ProductCard> findBySearch(@RequestBody ProductSearchRequest model) {
        return productService.findBySearch(model);
    }

    @GetMapping("/getAll/{id}")
    public List<ProductProxy> getAll(@PathVariable Integer id) {
        return productService.getAll(id);
    }

}
