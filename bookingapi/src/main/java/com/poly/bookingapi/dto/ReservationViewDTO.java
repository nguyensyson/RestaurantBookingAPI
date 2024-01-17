package com.poly.bookingapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poly.bookingapi.entity.*;
import com.poly.bookingapi.proxydto.DinnerTableProxy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationViewDTO {

    private Integer id;
    private String sdt;
    private String fullname;
    private Integer numberOfPeopleBooked;
    private LocalDateTime reservationDate;
    private Integer idCategoryDiningRoom;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ReservationStatus oderStatus;
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<DiningRoom> diningRoom;
    private List<DinnerTableProxy> dinnerTables;
//    private List<DinnerTable> dinnerTables;
//    private Integer idClient;
//    private Integer idVoucher;
//    private LocalTime startTime;
//    private LocalTime delayTime;
    private Long upfrontPrice;
    private Long originalPrice;
    private Long actualPrice;
    private Long priceToPay;
    private LocalDate createdAt;
//    private List<ReservationProduct> listReservationPorduct;

}



