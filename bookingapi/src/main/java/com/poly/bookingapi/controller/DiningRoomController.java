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
    public ResponseEntity<List<DiningRoom>> getAllDiningRoom(){
        return ResponseEntity.ok(diningRoomService.getAll());
    }

    @GetMapping("api/view/dining-room/get-by-id-category/{id}")
    public ResponseEntity<List<DiningRoom>> getByIdCategory(@PathVariable Integer id){
        return ResponseEntity.ok(diningRoomService.getByIdCategory(id));
    }

    @GetMapping("api/view/dining-room/detail/{id}")
    public ResponseEntity<DiningRoom> detail(@PathVariable Integer id){
        return ResponseEntity.ok(diningRoomService.detail(id));
    }

    @PostMapping("api/admin/dining-room/add")
    public ResponseEntity<DiningRoom>addDiningRoom(@RequestBody DiningRoomDTO diningRoomDTO){
        return ResponseEntity.ok(diningRoomService.add(diningRoomDTO));
    }

    @PostMapping("api/admin/dining-room/update/{id}")
    public ResponseEntity<DiningRoom>updateDiningRoom(@PathVariable Integer id, @RequestBody DiningRoomDTO diningRoomDTO){
        return ResponseEntity.ok(diningRoomService.update(diningRoomDTO, id));
    }
}
