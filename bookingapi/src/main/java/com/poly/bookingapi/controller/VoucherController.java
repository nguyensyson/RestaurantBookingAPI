package com.poly.bookingapi.controller;

import com.poly.bookingapi.dto.VoucherDTO;
import com.poly.bookingapi.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/voucher")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @GetMapping("/getAll")
    public ResponseEntity<?>getAll(){
        return ResponseEntity.ok(voucherService.getALl());
    }

    @PostMapping("/add")
    public ResponseEntity<?>addVoucher(@RequestBody VoucherDTO voucherDTO){
        return ResponseEntity.ok(voucherService.add(voucherDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?>updateVoucher(@RequestBody VoucherDTO voucherDTO,@PathVariable Integer id){
        return ResponseEntity.ok(voucherService.update(voucherDTO, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?>deleteVoucher(@PathVariable Integer id){
        return ResponseEntity.ok(voucherService.delete(id));
    }
}
