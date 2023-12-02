package com.poly.bookingapi.repository;

import com.poly.bookingapi.entity.Client;
import com.poly.bookingapi.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    @Query(value = "select count(*) from reservation ",nativeQuery = true)
    Integer countReservation();

    @Query(value ="Select * from Reservation where client_id = :id order by id desc",nativeQuery = true)
    List<Reservation> getReservationByUser(@Param("id") Integer id);

    Reservation getByClient(Client client);
}
