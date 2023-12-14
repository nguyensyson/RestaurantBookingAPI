package com.poly.bookingapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationSortRequest extends PageModel{

    private String sortBy;
    private Integer statusID;
}
