package com.poly.bookingapi.repository;

import com.poly.bookingapi.entity.Account;
import com.poly.bookingapi.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {
    Discount getById(Integer id);
}
