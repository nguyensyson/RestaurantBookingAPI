package com.poly.bookingapi.controller;

import com.poly.bookingapi.dto.AccountDTO;
import com.poly.bookingapi.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/account/add")
    public ResponseEntity<String> addAccount(@RequestBody AccountDTO dto) {
        return ResponseEntity.ok(accountService.createAccount(dto));
    }

    @GetMapping("/hello")
    public String hello() {return "hello";}

}
