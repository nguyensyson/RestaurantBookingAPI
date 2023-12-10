package com.poly.bookingapi.repository;

import com.poly.bookingapi.entity.DiningRoom;
import com.poly.bookingapi.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiningRoomRepository extends JpaRepository<DiningRoom, Integer> {

    @Query("SELECT p " +
            "FROM DiningRoom p " +
            "WHERE p.category.id = :id")
    List<DiningRoom> getByIdCategory(@Param("id") Integer id);
}
