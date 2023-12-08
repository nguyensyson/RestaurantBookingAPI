package com.poly.bookingapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChangePlacesDTO {
    private Integer numberOfPeopleBooked;
    private Integer idCategoryDiningRoom;
    private Integer idRoom;
    private List<Integer> idTable;
}
