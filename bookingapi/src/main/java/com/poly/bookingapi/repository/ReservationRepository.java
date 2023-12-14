package com.poly.bookingapi.repository;

import com.poly.bookingapi.entity.Client;
import com.poly.bookingapi.entity.Product;
import com.poly.bookingapi.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    @Query(value = "select count(*) from reservation ",nativeQuery = true)
    Integer countReservation();

    @Query("SELECT p " +
            "FROM Reservation p " +
            "WHERE p.client.id = :id order by p.id desc")
    Page<Reservation> getReservationByUser(@Param("id") Integer id, Pageable pageable);

    @Query("SELECT p " +
            "FROM Reservation p " +
            "order by p.id desc")
    Page<Reservation> getAll(Pageable pageable);

    Reservation getByClient(Client client);

    @Query("SELECT p " +
            "FROM Reservation p " +
            "WHERE p.status.id = :id order by p.id desc")
    Page<Reservation> getByStatus(@Param("id") Integer id, Pageable pageable);
}
