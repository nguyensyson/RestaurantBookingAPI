package com.poly.bookingapi.controller;

import com.poly.bookingapi.dto.DiscountDTO;
import com.poly.bookingapi.respon.MessageResponse;
import com.poly.bookingapi.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @PostMapping("/add")
    public ResponseEntity<?>add(@RequestBody DiscountDTO discountDTO){
        return ResponseEntity.ok(discountService.add(discountDTO));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody DiscountDTO discountDTO,@PathVariable Integer id){
        return ResponseEntity.ok(discountService.update(discountDTO, id));
    }

    @PutMapping("/enable/{id}")
    public ResponseEntity<?> enabled(@PathVariable Integer id){
        return ResponseEntity.ok(discountService.enable(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?>delete(@PathVariable Integer id){
        return ResponseEntity.ok(discountService.delete(id));
    }
}
