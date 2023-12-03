package com.poly.bookingapi.controller;

import com.poly.bookingapi.dto.DiningRoomDTO;
import com.poly.bookingapi.respon.MessageResponse;
import com.poly.bookingapi.service.DiningRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DiningRoomController {

    @Autowired
    private DiningRoomService diningRoomService;

    @GetMapping("api/view/dining-room/get")
    public ResponseEntity<?> getAllDiningRoom(){
        return ResponseEntity.ok(diningRoomService.getAll());
    }

    @PostMapping("api/admin/dining-room/add")
    public ResponseEntity<?>addDiningRoom(@RequestBody DiningRoomDTO diningRoomDTO){
        return ResponseEntity.ok(diningRoomService.add(diningRoomDTO));
    }

    @PutMapping("api/admin/dining-room/update/{id}")
    public ResponseEntity<?>updateDiningRoom(@RequestBody DiningRoomDTO diningRoomDTO, @PathVariable Integer id){
        diningRoomService.update(diningRoomDTO,id);
        return ResponseEntity.ok(new MessageResponse("update dining room success"));
    }

}
