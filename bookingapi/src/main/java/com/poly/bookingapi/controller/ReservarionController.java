package com.poly.bookingapi.controller;

import com.poly.bookingapi.dto.ReservationDTO;
import com.poly.bookingapi.entity.CategoryDiningRoom;
import com.poly.bookingapi.entity.DiningRoom;
import com.poly.bookingapi.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/reservation/api")
public class ReservarionController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/getReservation")
    public ResponseEntity<?>getAll(){
        return ResponseEntity.ok(reservationService.getAll());
    }

    @PostMapping("/addByUser")
    public ResponseEntity<?>addByUser(@RequestBody ReservationDTO reservationDTO){
        return  ResponseEntity.ok(reservationService.addByUser(reservationDTO));
    }

    @PutMapping("/checkin/{id}")
    public  ResponseEntity<?>checkIn(@RequestBody ReservationDTO reservationDTO,
                                     @RequestBody CategoryDiningRoom categoryDiningRoom,
                                     @RequestBody DiningRoom diningRoom,
                                     @PathVariable Integer id){
        reservationService.addDiningRoom(categoryDiningRoom,categoryDiningRoom.getId());
        reservationService.addDinnerTable(diningRoom,diningRoom.getId());
        return ResponseEntity.ok(reservationService.checkIn(reservationDTO, id));
    }
}
