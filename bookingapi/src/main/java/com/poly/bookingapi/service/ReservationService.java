package com.poly.bookingapi.service;

import com.poly.bookingapi.dto.ReservationAddDTO;
import com.poly.bookingapi.dto.ReservationViewDTO;
import com.poly.bookingapi.entity.CategoryDiningRoom;
import com.poly.bookingapi.entity.DiningRoom;
import com.poly.bookingapi.entity.Reservation;

import java.util.List;
import java.util.Optional;

public interface
ReservationService {
    List<ReservationViewDTO> getAll();
    String addByUser(ReservationAddDTO reservationAddDTO);
    void addByAdmin(ReservationAddDTO reservationAddDTO);
    void addDiningRoom(CategoryDiningRoom categoryDiningRoom,Integer idRoom);
    void addDinnerTable(DiningRoom diningRoom, Integer idTable);
    Integer countReservation();
//    void addVoucher(Reservation reservation,Integer voucher);
    void checkIn(ReservationAddDTO reservationAddDTO, Integer id);
    Optional<Reservation> detailReservation(Integer id);
    void updateByClient(ReservationAddDTO reservationAddDTO, Integer id);
    List<Reservation> getReservationByUser(Integer id);
}
