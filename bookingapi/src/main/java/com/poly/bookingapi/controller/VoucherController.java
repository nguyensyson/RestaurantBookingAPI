package com.poly.bookingapi.controller;

import com.poly.bookingapi.dto.VoucherDTO;
import com.poly.bookingapi.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @GetMapping("api/view/voucher/get-all")
    public ResponseEntity<?>getAll(){
        return ResponseEntity.ok(voucherService.getALl());
    }

    @PostMapping("api/admin/voucher/add")
    public ResponseEntity<?>addVoucher(@RequestBody VoucherDTO voucherDTO){
        return ResponseEntity.ok(voucherService.add(voucherDTO));
    }

    @PutMapping("api/admin/voucher/update/{id}")
    public ResponseEntity<?>updateVoucher(@RequestBody VoucherDTO voucherDTO,@PathVariable Integer id){
        return ResponseEntity.ok(voucherService.update(voucherDTO, id));
    }

}
