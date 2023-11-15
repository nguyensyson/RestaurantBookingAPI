package com.poly.bookingapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DinnerTableDTO {

    private Integer numberOfSeats;
    private Integer status;
}
