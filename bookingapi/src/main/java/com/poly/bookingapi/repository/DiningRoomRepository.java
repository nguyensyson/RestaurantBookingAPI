package com.poly.bookingapi.repository;

import com.poly.bookingapi.entity.DiningRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiningRoomRepository extends JpaRepository<DiningRoom, Integer> {
}
