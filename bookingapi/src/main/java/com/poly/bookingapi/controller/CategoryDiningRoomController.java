package com.poly.bookingapi.controller;

import com.poly.bookingapi.dto.CategoryDiningRoomDTO;
import com.poly.bookingapi.dto.CategoryViewDTO;
import com.poly.bookingapi.service.CategoryDiningRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/view")
public class CategoryDiningRoomController {

    @Autowired
    private CategoryDiningRoomService service;

    @GetMapping("/category-room/get-all")
    public ResponseEntity<List<CategoryDiningRoomDTO>> getALl() {
        return ResponseEntity.ok(service.getALl());
    }
}
