package com.poly.bookingapi.service;

import com.poly.bookingapi.dto.DiscountDTO;
import com.poly.bookingapi.entity.Discount;

public interface DiscountService {

    Discount add(DiscountDTO discountDTO);
    Discount update(DiscountDTO discountDTO,Integer id);
    Discount delete(Integer id);
    Discount getById(Integer id);
    Discount enable(Integer id);
}
