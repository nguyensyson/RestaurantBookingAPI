package com.poly.bookingapi.repository;

import com.poly.bookingapi.entity.Product;
import com.poly.bookingapi.entity.Reservation;
import com.poly.bookingapi.entity.ReservationProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationProductRepository extends JpaRepository<ReservationProduct, Integer> {

    @Query("SELECT t FROM ReservationProduct t "
            + "WHERE t.reservation.id = :idReservation AND t.product.id = :idProduct")
    ReservationProduct findByReservationAndProduct(@Param("idReservation") Integer idReservation,
                                                   @Param("idProduct") Integer idProduct);
}
