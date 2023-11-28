package com.poly.bookingapi.repository;

import com.poly.bookingapi.entity.Client;
import com.poly.bookingapi.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    @Query(value = "select count(*) from reservation ",nativeQuery = true)
    Integer countReservation();
    Reservation getByClient(Client client);
}
