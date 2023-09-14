package com.poly.bookingapi.repository;

import com.poly.bookingapi.entity.Account;
import com.poly.bookingapi.entity.ImagePage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagePageRepository extends JpaRepository<ImagePage, Integer> {
}
