package com.poly.bookingapi.service;

import com.poly.bookingapi.dto.ReservationDTO;
import com.poly.bookingapi.entity.CategoryDiningRoom;
import com.poly.bookingapi.entity.DiningRoom;
import com.poly.bookingapi.entity.Reservation;

import java.util.List;

public interface
ReservationService {
    List<ReservationDTO> getAll();
    void addByUser(ReservationDTO reservationDTO);
    void addByAdmin(ReservationDTO reservationDTO);
    void addDiningRoom(CategoryDiningRoom categoryDiningRoom,Integer idRoom);
    void addDinnerTable(DiningRoom diningRoom, Integer idTable);
    Integer countReservation();
//    void addVoucher(Reservation reservation,Integer voucher);
    void checkIn(ReservationDTO reservationDTO, Integer id);
}
