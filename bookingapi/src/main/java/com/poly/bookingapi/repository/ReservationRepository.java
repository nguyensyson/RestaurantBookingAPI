package com.poly.bookingapi.repository;

import com.poly.bookingapi.entity.Client;
import com.poly.bookingapi.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByClientOrderByReservationDateDesc(Optional<Client> client);

}
