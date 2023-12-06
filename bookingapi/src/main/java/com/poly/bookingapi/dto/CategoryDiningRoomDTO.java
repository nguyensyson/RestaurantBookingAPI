package com.poly.bookingapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDiningRoomDTO {

    private Integer idDinningRoom;
    private String title;
    private String introduce;
    private BigDecimal fee;
    private List<ReservationViewDTO> listReservation;
    private List<DiningRoomDTO> listDining;
}
