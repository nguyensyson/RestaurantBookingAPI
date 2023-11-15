package com.poly.bookingapi.service.impl;

import com.poly.bookingapi.dto.VoucherDTO;
import com.poly.bookingapi.entity.Voucher;
import com.poly.bookingapi.repository.VoucherRepository;
import com.poly.bookingapi.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    @Override
    public List<VoucherDTO> getALl() {
        List<Voucher> getList = voucherRepository.findAll();
        List<VoucherDTO> getListDTO = new ArrayList<>();
        for (Voucher i: getList) {
            VoucherDTO voucherDTO = new VoucherDTO();
            VoucherDTO dto = i.loadData(voucherDTO);
            getListDTO.add(dto);
        }
        return getListDTO;
    }

    @Override
    public Voucher add(VoucherDTO voucherDTO) {
        Voucher voucher = new Voucher();
        voucher.setTitle(voucherDTO.getTitle());
        voucher.setVoucherValue(voucherDTO.getVoucherValue());
        voucher.setStatus(voucherDTO.getStatus());
        voucher.setRequirement(voucherDTO.getRequirement());
        voucher.setStartDate(voucherDTO.getStartDate());
        voucher.setEndDate(voucherDTO.getEndDate());
        return voucherRepository.save(voucher);
    }

    @Override
    public Voucher update(VoucherDTO voucherDTO, Integer id) {
        Optional<Voucher> optional = voucherRepository.findById(id);
        return optional.map(voucher -> {
            voucher.setTitle(voucherDTO.getTitle());
            voucher.setVoucherValue(voucherDTO.getVoucherValue());
            voucher.setStatus(voucherDTO.getStatus());
            voucher.setRequirement(voucherDTO.getRequirement());
            voucher.setStartDate(voucherDTO.getStartDate());
            voucher.setEndDate(voucherDTO.getEndDate());
            return voucherRepository.save(voucher);
        }).orElse(null);
    }

    @Override
    public Voucher delete(Integer id) {
        Optional<Voucher> optional = voucherRepository.findById(id);
        return optional.map(voucher -> {
            voucherRepository.delete(voucher);
            return voucher;
        }).orElse(null);
    }
}
