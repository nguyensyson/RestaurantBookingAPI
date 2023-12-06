package com.poly.bookingapi.dto;

import com.poly.bookingapi.entity.Product;
import com.poly.bookingapi.entity.ReservationProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationAddDTO {

    private Integer id;
    private String sdt;
    private String fullname;
    private Integer numberOfPeopleBooked;
    private LocalDateTime dateTime;
    private Integer idCategoryDiningRoom;
    private Integer idClient;
    private Integer idVoucher;
    private Long upfrontPrice;
    private Long originalPrice;
    private Long actualPrice;
    private Long priceToPay;
    private List<ProductDTO> listPorduct;

}



