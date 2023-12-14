package com.poly.bookingapi.controller;

import com.poly.bookingapi.dto.DiscountDTO;
import com.poly.bookingapi.entity.Discount;
import com.poly.bookingapi.respon.MessageResponse;
import com.poly.bookingapi.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @PostMapping("api/admin/discount/add")
    public ResponseEntity<Discount>add(@RequestBody DiscountDTO discountDTO){
        return ResponseEntity.ok(discountService.add(discountDTO));
    }


    @PutMapping("api/admin/discount/update/{id}")
    public ResponseEntity<Discount> update(@RequestBody DiscountDTO discountDTO,@PathVariable Integer id){
        return ResponseEntity.ok(discountService.update(discountDTO, id));
    }

    @PutMapping("api/admin/discount/enable/{id}")
    public ResponseEntity<Discount> enabled(@PathVariable Integer id){
        return ResponseEntity.ok(discountService.enable(id));
    }

}
