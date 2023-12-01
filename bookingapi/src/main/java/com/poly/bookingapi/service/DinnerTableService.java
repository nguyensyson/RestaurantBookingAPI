package com.poly.bookingapi.service;

import com.poly.bookingapi.dto.DinnerTableDTO;
import com.poly.bookingapi.entity.DinnerTable;

import java.util.List;

public interface DinnerTableService {
    List<DinnerTableDTO>getAll();
    DinnerTable add(DinnerTableDTO dinnerTableDTO);
    DinnerTable update(DinnerTableDTO dinnerTableDTO,Integer id);
    DinnerTable delete(Integer id);
}
