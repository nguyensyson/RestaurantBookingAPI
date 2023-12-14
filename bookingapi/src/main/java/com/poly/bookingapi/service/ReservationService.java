package com.poly.bookingapi.service;

import com.poly.bookingapi.dto.*;
import com.poly.bookingapi.entity.Reservation;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface
ReservationService {
    Page<ReservationViewDTO> getAll(ReservationSortRequest model);
    Page<ReservationViewDTO> getByStatus(ReservationSortRequest model);
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
    Page<ReservationViewDTO> getReservationByUser(Integer id, ReservationSortRequest model);
    String changePlaces(ChangePlacesDTO dto, Integer idResercation);
    String changeProduct(ChangeProductDTO dto, Integer idResercation);
    String arrangeSeats(ChangePlacesDTO dto, Integer idResercation);
}
