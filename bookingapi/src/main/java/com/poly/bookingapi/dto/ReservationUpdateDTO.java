package com.poly.bookingapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationUpdateDTO {

    private Integer id;
    private String sdt;
    private String fullname;
    private Integer numberOfPeopleBooked;
    private LocalDateTime dateTime;
    private Integer idCategoryDiningRoom;
    private Long upfrontPrice;
    private Long originalPrice;
    private Long actualPrice;
    private Long priceToPay;
    private List<ProductDTO> listPorduct;
    private Integer idRoom;
    private List<Integer> idTable;
    private Integer status;

}



