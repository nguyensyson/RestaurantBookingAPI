package com.poly.bookingapi.controller;

import com.poly.bookingapi.dto.DiningRoomDTO;
import com.poly.bookingapi.entity.DiningRoom;
import com.poly.bookingapi.respon.MessageResponse;
import com.poly.bookingapi.service.DiningRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DiningRoomController {

    @Autowired
    private DiningRoomService diningRoomService;

    @GetMapping("api/view/dining-room/get")
    public ResponseEntity<List<DiningRoomDTO>> getAllDiningRoom(){
        return ResponseEntity.ok(diningRoomService.getAll());
    }

    @GetMapping("api/view/dining-room/get-by-id-category/{id}")
    public ResponseEntity<List<DiningRoomDTO>> getByIdCategory(@PathVariable Integer id){
        return ResponseEntity.ok(diningRoomService.getByIdCategory(id));
    }

    @PostMapping("api/admin/dining-room/add")
    public ResponseEntity<DiningRoom>addDiningRoom(@RequestBody DiningRoomDTO diningRoomDTO){
        return ResponseEntity.ok(diningRoomService.add(diningRoomDTO));
    }

}
