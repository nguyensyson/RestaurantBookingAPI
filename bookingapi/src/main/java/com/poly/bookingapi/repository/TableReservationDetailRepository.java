package com.poly.bookingapi.repository;

import com.poly.bookingapi.entity.TableReservationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableReservationDetailRepository extends JpaRepository<TableReservationDetail, Integer> {
}
