package com.poly.bookingapi.repository;

import com.poly.bookingapi.entity.ComboDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComboDetailRepository extends JpaRepository<ComboDetail, Integer> {
}
