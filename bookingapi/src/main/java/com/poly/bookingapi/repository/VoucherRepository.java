package com.poly.bookingapi.repository;

import com.poly.bookingapi.entity.Product;
import com.poly.bookingapi.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Integer> {
    Voucher getById(Integer id);

    @Query("SELECT p " +
            "FROM Voucher p " +
            "WHERE p.title LIKE :title")
    Voucher getByTitle(@Param("title") String title);
}
