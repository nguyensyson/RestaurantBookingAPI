package com.poly.bookingapi.controller;

import com.poly.bookingapi.dto.DinnerTableDTO;
import com.poly.bookingapi.service.DinnerTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DinnerTableController {

    @Autowired
    private DinnerTableService dinnerTableService;

    @GetMapping("/getAllDinnerTable")
    public ResponseEntity<?> getAllDinnerTable(){
        return ResponseEntity.ok(dinnerTableService.getAll());
    }

    @PostMapping("/addDinnerTable")
    public ResponseEntity<?>addDinnerTable(@RequestBody DinnerTableDTO dinnerTableDTO){
        return ResponseEntity.ok(dinnerTableService.add(dinnerTableDTO));
    }

    @PutMapping("/updateDinnerTable/{id}")
    public ResponseEntity<?>updateDinnerTable(@RequestBody DinnerTableDTO dinnerTableDTO, @PathVariable Integer id){
        return ResponseEntity.ok(dinnerTableService.update(dinnerTableDTO, id));
    }

    @DeleteMapping("/deleteDinnerTable/{id}")
    public ResponseEntity<?>deleteDinnerTable(@PathVariable Integer id){
        return ResponseEntity.ok(dinnerTableService.delete(id));
    }
}
