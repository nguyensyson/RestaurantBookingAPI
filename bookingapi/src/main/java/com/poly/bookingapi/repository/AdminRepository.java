package com.poly.bookingapi.repository;

import com.poly.bookingapi.entity.Admin;
import com.poly.bookingapi.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    @Query("SELECT c FROM Admin c WHERE c.account.id = :id")
    Admin getByAccount(@Param("id") Integer id);
}
