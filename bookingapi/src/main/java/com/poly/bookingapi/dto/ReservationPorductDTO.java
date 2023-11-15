package com.poly.bookingapi.dto;

import com.poly.bookingapi.entity.Product;
import com.poly.bookingapi.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationPorductDTO {

    private Product product;
    private Reservation reservation;
    private Integer quantity;

}
