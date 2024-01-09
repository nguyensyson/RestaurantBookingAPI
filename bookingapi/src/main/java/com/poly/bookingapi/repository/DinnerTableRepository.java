package com.poly.bookingapi.repository;

import com.poly.bookingapi.entity.DinnerTable;
import com.poly.bookingapi.proxydto.DinnerTableProxy;
import com.poly.bookingapi.proxydto.ProductProxy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DinnerTableRepository extends JpaRepository<DinnerTable, Integer> {
    @Query("select dt.id as id, dt.tableCode as tableCode,dt.numberOfSeats as numberOfSeats ,dt.status as status" +
        " from DinnerTable dt where dt.diningRoom.id = :id")
    List<DinnerTableProxy> getAllByDiningRoomId(Integer id);

    @Query("SELECT d.id AS id, " +
            "d.tableCode as tableCode,d.numberOfSeats as numberOfSeats ,d.status as status ," +
            "(CASE WHEN COUNT(DISTINCT t.id) > 0 THEN TRUE ELSE FALSE END ) AS isOrdered FROM " +
            "DinnerTable d LEFT JOIN TableDetail t ON d.id = t.dinnerTable.id " +
            "AND t.reservation.id = :id WHERE d.diningRoom.id = :idRoom " +
            "GROUP BY d.id " +
            "ORDER BY (CASE WHEN COUNT(DISTINCT t.id) > 0 THEN TRUE ELSE FALSE END ) DESC")
    List<DinnerTableProxy> getAllDESC(@Param("id") Integer id, @Param("idRoom") Integer idRoom);

    @Query("SELECT t FROM DinnerTable t ORDER BY t.status DESC")
    List<DinnerTable> getAll();

    @Query("SELECT t " +
            "FROM DinnerTable t INNER JOIN TableDetail td ON t.id = td.dinnerTable.id " +
            "WHERE td.reservation.id = :id")
    List<DinnerTable> getByReservation(@Param("id") Integer id);
}
