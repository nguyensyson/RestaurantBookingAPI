package com.poly.bookingapi.repository;

import com.poly.bookingapi.entity.ProductEvaluate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductEvaluateRepository extends JpaRepository<ProductEvaluate, Integer> {
}
