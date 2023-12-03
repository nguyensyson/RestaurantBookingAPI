package com.poly.bookingapi.controller;

import com.poly.bookingapi.dto.ReservationDTO;
import com.poly.bookingapi.entity.CategoryDiningRoom;
import com.poly.bookingapi.entity.DiningRoom;
import com.poly.bookingapi.entity.Reservation;
import com.poly.bookingapi.respon.MessageResponse;
import com.poly.bookingapi.respon.NotFoundException;
import com.poly.bookingapi.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ReservarionController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/api/view/reservation/get-all")
    public ResponseEntity<?>getAll(){
        return ResponseEntity.ok(reservationService.getAll());
    }

    @GetMapping("/api/view/reservation/getCountReservation")
    public ResponseEntity<?>getCountReservaion(){
        return ResponseEntity.ok(reservationService.countReservation());
    }

    @GetMapping("/api/view/reservation/getAllByClient")
    public ResponseEntity<List<Reservation>>getAllByClient(@RequestParam(value = "id") Integer id){
        return ResponseEntity.ok(reservationService.getReservationByUser(id));
    }

    @GetMapping("/api/view/reservation/detail/{id}")
    public ResponseEntity<?> detailReservation(@PathVariable("id") Integer id){
        return ResponseEntity.ok(reservationService.detailReservation(id).orElseThrow(() -> new NotFoundException("Not Found Reservation")));
    }

    @PostMapping("/api/admin/reservation/addByUser")
    public ResponseEntity<?>addByUser(@RequestBody ReservationDTO reservationDTO){
        reservationService.addByUser(reservationDTO);
        return  ResponseEntity.ok(new MessageResponse("add reservation by user success"));
    }

    @PostMapping("/api/admin/reservation/addByAdmin")
    public ResponseEntity<?>addByAdmin(@RequestBody ReservationDTO reservationDTO){
        reservationService.addByAdmin(reservationDTO);
        return  ResponseEntity.ok(new MessageResponse("add reservation by admin success"));
    }

    @PutMapping("/api/admin/reservation/checkin/{id}")
    public  ResponseEntity<?>checkIn(@RequestBody ReservationDTO reservationDTO,
                                     @PathVariable Integer id,
                                     @RequestBody CategoryDiningRoom categoryDiningRoom,
                                     @RequestBody DiningRoom diningRoom){
        reservationService.addDiningRoom(categoryDiningRoom,categoryDiningRoom.getId());
        reservationService.addDinnerTable(diningRoom,diningRoom.getId());
        reservationService.checkIn(reservationDTO, id);
        return ResponseEntity.ok(new MessageResponse("update reservation success"));
    }

    @PutMapping("/api/admin/reservation/updateByUser/{id}")
    public  ResponseEntity<?>updateByUser(@RequestBody ReservationDTO reservationDTO,
                                          @PathVariable Integer id){
        reservationService.updateByClient(reservationDTO, id);
        return ResponseEntity.ok(new MessageResponse("update reservation success"));
    }
}


