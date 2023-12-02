package com.poly.bookingapi.controller;

import com.poly.bookingapi.dto.ReservationDTO;
import com.poly.bookingapi.entity.CategoryDiningRoom;
import com.poly.bookingapi.entity.DiningRoom;
import com.poly.bookingapi.respon.MessageResponse;
import com.poly.bookingapi.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin/reservation")
public class ReservarionController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/get")
    public ResponseEntity<?>getAll(){
        return ResponseEntity.ok(reservationService.getAll());
    }

    @PostMapping("/addByUser")
    public ResponseEntity<?>addByUser(@RequestBody ReservationDTO reservationDTO){
        reservationService.addByUser(reservationDTO);
        return  ResponseEntity.ok(new MessageResponse("add reservation by user success"));
    }

    @PostMapping("/addByAdmin")
    public ResponseEntity<?>addByAdmin(@RequestBody ReservationDTO reservationDTO){
        reservationService.addByAdmin(reservationDTO);
        return  ResponseEntity.ok(new MessageResponse("add reservation by user success"));
    }

    @PutMapping("/checkin/{id}")
    public  ResponseEntity<?>checkIn(@RequestBody ReservationDTO reservationDTO,
                                     @PathVariable Integer id,
                                     @RequestBody CategoryDiningRoom categoryDiningRoom,
                                     @RequestBody DiningRoom diningRoom){
        reservationService.addDiningRoom(categoryDiningRoom,categoryDiningRoom.getId());
        reservationService.addDinnerTable(diningRoom,diningRoom.getId());
        reservationService.checkIn(reservationDTO, id);
        return ResponseEntity.ok(new MessageResponse("add reservation by user success"));
    }


}


