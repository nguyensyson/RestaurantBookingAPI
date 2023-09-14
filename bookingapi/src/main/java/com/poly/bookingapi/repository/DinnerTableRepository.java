package com.poly.bookingapi.repository;

import com.poly.bookingapi.entity.DinnerTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DinnerTableRepository extends JpaRepository<DinnerTable, Integer> {
}
