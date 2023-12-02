package com.poly.bookingapi.controller;

import com.poly.bookingapi.dto.DiningRoomDTO;
import com.poly.bookingapi.respon.MessageResponse;
import com.poly.bookingapi.service.DiningRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DiningRoomController {

    @Autowired
    private DiningRoomService diningRoomService;

    @GetMapping("/getAllDiningRoom")
    public ResponseEntity<?> getAllDiningRoom(){
        return ResponseEntity.ok(diningRoomService.getAll());
    }

    @PostMapping("/addDiningRoom")
    public ResponseEntity<?>addDiningRoom(@RequestBody DiningRoomDTO diningRoomDTO){
        return ResponseEntity.ok(diningRoomService.add(diningRoomDTO));
    }

    @PutMapping("/updateDiningRoom/{id}")
    public ResponseEntity<?>updateDiningRoom(@RequestBody DiningRoomDTO diningRoomDTO, @PathVariable Integer id){
        diningRoomService.update(diningRoomDTO,id);
        return ResponseEntity.ok(new MessageResponse("update dining room success"));
    }

    @DeleteMapping("/deleteDiningRoom/{id}")
    public ResponseEntity<?>deleteDiningRoom(@PathVariable Integer id){
        return ResponseEntity.ok(diningRoomService.delete(id));
    }

}
