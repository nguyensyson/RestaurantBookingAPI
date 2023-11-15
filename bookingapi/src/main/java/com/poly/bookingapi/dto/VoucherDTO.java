package com.poly.bookingapi.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoucherDTO {

    private String title;

    private Integer voucherValue;

    private Integer status;

    private BigDecimal requirement;

    private LocalDate startDate;

    private LocalDate endDate;


}
