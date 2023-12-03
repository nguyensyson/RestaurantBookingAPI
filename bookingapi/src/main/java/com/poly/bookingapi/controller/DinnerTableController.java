package com.poly.bookingapi.controller;

import com.poly.bookingapi.dto.DinnerTableDTO;
import com.poly.bookingapi.service.DinnerTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DinnerTableController {

    @Autowired
    private DinnerTableService dinnerTableService;

    @GetMapping("api/view/dinner-table/get-all")
    public ResponseEntity<?> getAllDinnerTable(){
        return ResponseEntity.ok(dinnerTableService.getAll());
    }

    @PostMapping("api/admin/dinner-table/add")
    public ResponseEntity<?>addDinnerTable(@RequestBody DinnerTableDTO dinnerTableDTO){
        return ResponseEntity.ok(dinnerTableService.add(dinnerTableDTO));
    }

    @PutMapping("api/admin/dinner-table/update/{id}")
    public ResponseEntity<?>updateDinnerTable(@RequestBody DinnerTableDTO dinnerTableDTO, @PathVariable Integer id){
        return ResponseEntity.ok(dinnerTableService.update(dinnerTableDTO, id));
    }

    @DeleteMapping("api/admin/dinner-table/delete/{id}")
    public ResponseEntity<?>deleteDinnerTable(@PathVariable Integer id){
        return ResponseEntity.ok(dinnerTableService.delete(id));
    }
}
