package com.poly.bookingapi.repository;

import com.poly.bookingapi.entity.ReservationProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationProductRepository extends JpaRepository<ReservationProduct, Integer> {
}
