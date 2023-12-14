package com.poly.bookingapi.service;

import com.poly.bookingapi.dto.DinnerTableDTO;
import com.poly.bookingapi.dto.DinnerTableRequest;
import com.poly.bookingapi.dto.ReservationSortRequest;
import com.poly.bookingapi.entity.DinnerTable;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DinnerTableService {

    Page<DinnerTableDTO> getAll(DinnerTableRequest model);
    DinnerTable add(DinnerTableDTO dinnerTableDTO);
    DinnerTable update(DinnerTableDTO dinnerTableDTO,Integer id);
    DinnerTable delete(Integer id);

}
