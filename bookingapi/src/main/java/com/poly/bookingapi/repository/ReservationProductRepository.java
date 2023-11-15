package com.poly.bookingapi.repository;

import com.poly.bookingapi.entity.Product;
import com.poly.bookingapi.entity.Reservation;
import com.poly.bookingapi.entity.ReservationProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationProductRepository extends JpaRepository<ReservationProduct, Integer> {
    ReservationProduct findByReservationAndProduct(Reservation reservation, Product product);
}
