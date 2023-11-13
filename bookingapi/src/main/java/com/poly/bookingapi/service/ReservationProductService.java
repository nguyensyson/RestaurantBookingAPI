package com.poly.bookingapi.service;

import com.poly.bookingapi.dto.ReservationPorductDTO;
import com.poly.bookingapi.entity.Product;
import com.poly.bookingapi.entity.Reservation;
import com.poly.bookingapi.entity.ReservationProduct;

public interface ReservationProductService {

    ReservationProduct getReservationAndProduct(Reservation reservation, Product product);

    ReservationProduct addReservationProduct(ReservationPorductDTO reservationPorductDTO);


}
