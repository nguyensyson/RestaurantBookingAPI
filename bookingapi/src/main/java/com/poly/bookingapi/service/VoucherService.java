package com.poly.bookingapi.service;

import com.poly.bookingapi.dto.VoucherDTO;
import com.poly.bookingapi.entity.Voucher;

import java.util.List;

public interface VoucherService {

    List<VoucherDTO>getALl();
    Voucher add(VoucherDTO voucherDTO);
    Voucher update(VoucherDTO voucherDTO,Integer id);
    Voucher delete(Integer id);

}
