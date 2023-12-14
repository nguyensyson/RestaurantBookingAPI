package com.poly.bookingapi.dto;

import com.poly.bookingapi.entity.DiningRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DinnerTableDTO {

    private Integer id;
    private String tableCode;
    private Integer numberOfSeats;
    private Integer status;
    private Integer idRoom;
}
