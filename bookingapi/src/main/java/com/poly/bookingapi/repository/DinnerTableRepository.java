package com.poly.bookingapi.repository;

import com.poly.bookingapi.entity.DinnerTable;
import com.poly.bookingapi.proxydto.DinnerTableProxy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DinnerTableRepository extends JpaRepository<DinnerTable, Integer> {
    @Query("select dt.id as id, dt.tableCode as tableCode,dt.numberOfSeats as numberOfSeats ,dt.status as status" +
        " from DinnerTable dt where dt.diningRoom.id = :id")
    List<DinnerTableProxy> getAllByDiningRoomId(Integer id);
}
