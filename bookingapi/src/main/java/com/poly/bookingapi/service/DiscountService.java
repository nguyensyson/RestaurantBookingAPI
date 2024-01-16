package com.poly.bookingapi.service;

import com.poly.bookingapi.dto.DiscountDTO;
import com.poly.bookingapi.dto.DiscountViewDTO;
import com.poly.bookingapi.entity.Discount;

import java.util.List;

public interface DiscountService {

    Discount add(DiscountDTO discountDTO);
    Discount update(DiscountDTO discountDTO,Integer id);
    Discount delete(Integer id);
    DiscountViewDTO getById(Integer id);
    Discount enable(Integer id);
    List<Discount> getALL();
}
