package com.poly.bookingapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {

    private Integer id;
    private String sdt;
    private Integer numberOfPeopleBooked;
    private LocalDate reservationDate;
    private Integer idCategoryDiningRoom;
    private Integer idStatus;
    private Integer idClient;
    private Integer idAdmin;
    private Integer idVoucher;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalTime delayTime;
    private Long upfrontPrice;
    private LocalDate createdAt;
    private List<ProductDTO> listProduct;
    private List<ReservationPorductDTO> listReservationPorduct;

}



