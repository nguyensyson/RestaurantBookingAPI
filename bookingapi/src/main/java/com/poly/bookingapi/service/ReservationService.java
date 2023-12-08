package com.poly.bookingapi.service;

import com.poly.bookingapi.dto.*;
import com.poly.bookingapi.entity.Reservation;

import java.util.List;
import java.util.Optional;

public interface
ReservationService {
    List<ReservationViewDTO> getAll();
    String addByUser(ReservationAddDTO reservationAddDTO);
    String addByAdmin(ReservationUpdateDTO reservationAddDTO);
//    void addDiningRoom(CategoryDiningRoom categoryDiningRoom,Integer idRoom);
//    void addDinnerTable(DiningRoom diningRoom, Integer idTable);
//    Integer countReservation();
//    void addVoucher(Reservation reservation,Integer voucher);
    String changeStatus(ChangeStatusDTO dto, Integer id);
    Optional<Reservation> detailReservation(Integer id);
    String updateByClient(ReservationAddDTO reservationAddDTO, Integer id);
    String updateByAdmin(ReservationUpdateDTO reservationAddDTO, Integer id);
    List<Reservation> getReservationByUser(Integer id);
    String changePlaces(ChangePlacesDTO dto, Integer idResercation);
    String changeProduct(ChangeProductDTO dto, Integer idResercation);
    String arrangeSeats(ChangePlacesDTO dto, Integer idResercation);
}
