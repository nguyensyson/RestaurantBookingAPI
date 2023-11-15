package com.poly.bookingapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiningRoomDTO {

    private Integer idDinnerTable;
    private Integer maximumOccupancy;
    private List<DinnerTableDTO> listDinnerTable;


}
