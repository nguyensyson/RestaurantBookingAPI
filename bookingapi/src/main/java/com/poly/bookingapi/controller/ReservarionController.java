package com.poly.bookingapi.controller;

import com.poly.bookingapi.dto.ReservationAddDTO;
import com.poly.bookingapi.dto.ReservationViewDTO;
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

    @GetMapping("/api/admin/reservation/get-all")
    public ResponseEntity<List<ReservationViewDTO>>getAll(){
        return ResponseEntity.ok(reservationService.getAll());
    }

    @GetMapping("/api/admin/reservation/getCountReservation")
    public ResponseEntity<Integer>getCountReservaion(){
        return ResponseEntity.ok(reservationService.countReservation());
    }

    @GetMapping("/api/user/reservation/getAllByClient/{id}")
    public ResponseEntity<List<Reservation>>getAllByClient(@PathVariable(value = "id") Integer id){
        return ResponseEntity.ok(reservationService.getReservationByUser(id));
    }

    @GetMapping("/api/user/reservation/detail/{id}")
    public ResponseEntity<?> detailReservation(@PathVariable("id") Integer id){
        return ResponseEntity.ok(reservationService.detailReservation(id).orElseThrow(() -> new NotFoundException("Not Found Reservation")));
    }

    @PostMapping("/api/view/reservation/addByUser")
    public ResponseEntity<?>addByUser(@RequestBody ReservationAddDTO dto){
        reservationService.addByUser(dto);
        return  ResponseEntity.ok(new MessageResponse("add reservation by user success"));
    }

    @PostMapping("/api/admin/reservation/addByAdmin")
    public ResponseEntity<?>addByAdmin(@RequestBody ReservationAddDTO dto){
        reservationService.addByAdmin(dto);
        return  ResponseEntity.ok(new MessageResponse("add reservation by admin success"));
    }

    @PutMapping("/api/admin/reservation/checkin/{id}")
    public  ResponseEntity<?>checkIn(@RequestBody ReservationAddDTO reservationAddDTO,
                                     @PathVariable Integer id,
                                     @RequestBody CategoryDiningRoom categoryDiningRoom,
                                     @RequestBody DiningRoom diningRoom){
        reservationService.addDiningRoom(categoryDiningRoom,categoryDiningRoom.getId());
        reservationService.addDinnerTable(diningRoom,diningRoom.getId());
        reservationService.checkIn(reservationAddDTO, id);
        return ResponseEntity.ok(new MessageResponse("update reservation success"));
    }

    @PutMapping("/api/user/reservation/updateByUser/{id}")
    public  ResponseEntity<?>updateByUser(@RequestBody ReservationAddDTO dto,
                                          @PathVariable Integer id){
        reservationService.updateByClient(dto, id);
        return ResponseEntity.ok(new MessageResponse("update reservation success"));
    }
}


