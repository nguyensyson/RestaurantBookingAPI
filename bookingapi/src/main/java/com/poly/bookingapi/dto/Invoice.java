package com.poly.bookingapi.dto;

import com.poly.bookingapi.proxydto.ProductProxy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    private Integer id;
    private String sdt;
    private String fullname;
    private Integer numberOfPeopleBooked;
    private LocalDateTime reservationDate;
    private String categoryDiningRoom;
    private String diningRoom;
    private String diningTable;
    private Integer oderStatus;
    private Long upfrontPrice;
    private Long originalPrice;
    private Long actualPrice;
    private Long priceToPay;
    private List<ProductProxy> products;
}
