package com.poly.bookingapi.repository;

import com.poly.bookingapi.entity.DiningRoom;
import com.poly.bookingapi.entity.Product;
import com.poly.bookingapi.proxydto.ProductProxy;
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

    @Query("SELECT d " +
            "FROM DiningRoom d LEFT JOIN TableDetail t ON d.id = t.diningRoom.id " +
            "AND t.reservation.id = :id WHERE d.category.id = :idcategory " +
            "GROUP BY d.id " +
            "ORDER BY (CASE WHEN COUNT(DISTINCT t.id) > 0 THEN TRUE ELSE FALSE END ) DESC")
    List<DiningRoom> getAllDESC(@Param("id") Integer id,@Param("idcategory") Integer idcategory);
}
