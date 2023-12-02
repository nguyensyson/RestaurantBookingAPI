package com.poly.bookingapi.service.impl;

import com.poly.bookingapi.dto.ReservationPorductDTO;
import com.poly.bookingapi.entity.Product;
import com.poly.bookingapi.entity.Reservation;
import com.poly.bookingapi.entity.ReservationProduct;
import com.poly.bookingapi.repository.ReservationProductRepository;
import com.poly.bookingapi.service.ReservationProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationProductServiceImpl implements ReservationProductService {

    @Autowired
    private ReservationProductRepository reservationProductRepository;

    @Override
    public ReservationProduct getReservationAndProduct(Reservation reservation, Product product) {
        return reservationProductRepository.findByReservationAndProduct(reservation,product);
    }

    @Override
    public ReservationProduct addReservationProduct(ReservationPorductDTO reservationPorductDTO) {
        ReservationProduct reservationProduct = new ReservationProduct();
        reservationProduct.setProduct(Product.builder().id(reservationPorductDTO.getProduct().getId()).build());
        reservationProduct.setQuantity(reservationPorductDTO.getQuantity());
        return reservationProductRepository.save(reservationProduct);
    }

}
