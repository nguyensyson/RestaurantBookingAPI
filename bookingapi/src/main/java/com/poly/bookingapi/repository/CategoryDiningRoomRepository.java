package com.poly.bookingapi.repository;

import com.poly.bookingapi.entity.CategoryDiningRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDiningRoomRepository extends JpaRepository<CategoryDiningRoom, Integer> {
}
