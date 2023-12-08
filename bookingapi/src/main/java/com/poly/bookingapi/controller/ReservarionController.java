package com.poly.bookingapi.controller;

import com.poly.bookingapi.dto.*;
import com.poly.bookingapi.entity.Reservation;
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

//    @GetMapping("/api/admin/reservation/getCountReservation")
//    public ResponseEntity<Integer>getCountReservaion(){
//        return ResponseEntity.ok(reservationService.countReservation());
//    }

    @GetMapping("/api/user/reservation/getAllByClient/{id}")
    public ResponseEntity<List<Reservation>>getAllByClient(@PathVariable(value = "id") Integer id){
        return ResponseEntity.ok(reservationService.getReservationByUser(id));
    }

    @GetMapping("/api/user/reservation/detail/{id}")
    public ResponseEntity<?> detailReservation(@PathVariable("id") Integer id){
        return ResponseEntity.ok(reservationService.detailReservation(id).orElseThrow(() -> new NotFoundException("Not Found Reservation")));
    }
    //tạo bởi khách hàng
    @PostMapping("/api/view/reservation/addByUser")
    public ResponseEntity<String>addByUser(@RequestBody ReservationAddDTO dto){
        return  ResponseEntity.ok(reservationService.addByUser(dto));
    }
    // tạo bởi admin
    @PostMapping("/api/admin/reservation/addByAdmin")
    public ResponseEntity<String>addByAdmin(@RequestBody ReservationUpdateDTO dto){
        return  ResponseEntity.ok(reservationService.addByAdmin(dto));
    }
    // đổi trạng thái: quản lý xác nhận, kh huỷ lịch ....
    @PutMapping("/api/user/reservation/change-status/{id}")
    public  ResponseEntity<String>changeStatus(@RequestBody ChangeStatusDTO dto,
                                     @PathVariable Integer id) {
        return ResponseEntity.ok(reservationService.changeStatus(dto, id));
    }
    // thêm món đổi món
    @PutMapping("/api/user/reservation/change-product/{id}")
    public  ResponseEntity<String>changeProduct(@RequestBody ChangeProductDTO dto,
                                                @PathVariable Integer id){
        return ResponseEntity.ok(reservationService.changeProduct(dto, id));
    }
    // đổi chỗ
    @PutMapping("/api/admin/reservation/change-places/{id}")
    public  ResponseEntity<String>changePlaces(@RequestBody ChangePlacesDTO dto,
                                           @PathVariable Integer id){
        return ResponseEntity.ok(reservationService.changePlaces(dto, id));
    }
    // xếp chỗ
    @PutMapping("/api/admin/reservation/arrange-seats/{id}")
    public  ResponseEntity<String>arrangeSeats(@RequestBody ChangePlacesDTO dto,
                                          @PathVariable Integer id){
        return ResponseEntity.ok(reservationService.arrangeSeats(dto, id));
    }

    @PutMapping("/api/admin/reservation/updateByadmin/{id}")
    public  ResponseEntity<?>updateByAdmin(@RequestBody ReservationUpdateDTO dto,
                                          @PathVariable Integer id){
        return ResponseEntity.ok(reservationService.updateByAdmin(dto, id));
    }
    // kh cập nhật thông tin phiếu
    @PutMapping("/api/user/reservation/updateByUser/{id}")
    public  ResponseEntity<?>updateByUser(@RequestBody ReservationAddDTO dto,
                                          @PathVariable Integer id){
        return ResponseEntity.ok(reservationService.updateByClient(dto, id));
    }
}


