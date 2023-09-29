package com.poly.bookingapi.repository;

import com.poly.bookingapi.entity.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationStatusRepository extends JpaRepository<ReservationStatus, Integer> {
}
