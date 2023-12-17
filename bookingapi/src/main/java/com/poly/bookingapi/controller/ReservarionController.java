package com.poly.bookingapi.controller;

import com.poly.bookingapi.dto.*;
import com.poly.bookingapi.entity.Reservation;
import com.poly.bookingapi.respon.NotFoundException;
import com.poly.bookingapi.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class ReservarionController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/api/admin/reservation/get-all")
    public ResponseEntity<Page<ReservationViewDTO>>getAll(@RequestBody ReservationSortRequest model){
        return ResponseEntity.ok(reservationService.getAll(model));
    }

    @PostMapping("/api/admin/reservation/get-by-status")
    public ResponseEntity<Page<ReservationViewDTO>>getByStatus(@RequestBody ReservationSortRequest model){
        return ResponseEntity.ok(reservationService.getByStatus(model));
    }

//    @GetMapping("/api/admin/reservation/getCountReservation")
//    public ResponseEntity<Integer>getCountReservaion(){
//        return ResponseEntity.ok(reservationService.countReservation());
//    }

    @PostMapping("/api/user/reservation/getAllByClient/{id}")
    public ResponseEntity<Page<ReservationViewDTO>>getAllByClient(@PathVariable(value = "id") Integer id, @RequestBody ReservationSortRequest model){
        return ResponseEntity.ok(reservationService.getReservationByUser(id, model));
    }

    @GetMapping("/api/admin/reservation/detail/{id}")
    public ResponseEntity<Reservation> detailReservation(@PathVariable("id") Integer id){
        return ResponseEntity.ok(reservationService.detailReservation(id));
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
    public  ResponseEntity<String>updateByAdmin(@RequestBody ReservationUpdateDTO dto,
                                          @PathVariable Integer id){
        return ResponseEntity.ok(reservationService.updateByAdmin(dto, id));
    }
    // kh cập nhật thông tin phiếu
    @PutMapping("/api/user/reservation/updateByUser/{id}")
    public  ResponseEntity<String>updateByUser(@RequestBody ReservationAddDTO dto,
                                          @PathVariable Integer id){
        return ResponseEntity.ok(reservationService.updateByClient(dto, id));
    }
}


