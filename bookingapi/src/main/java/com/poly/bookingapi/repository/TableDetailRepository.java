package com.poly.bookingapi.repository;

import com.poly.bookingapi.entity.TableDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableDetailRepository extends JpaRepository<TableDetail, Integer> {

    @Query("SELECT t FROM TableDetail t "
            + "WHERE t.reservation.id = :id")
    List<TableDetail> getByReservationId(Integer id);
}
