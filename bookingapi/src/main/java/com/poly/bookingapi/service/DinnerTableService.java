package com.poly.bookingapi.service;

import com.poly.bookingapi.dto.DinnerTableDTO;
import com.poly.bookingapi.dto.DinnerTableRequest;
import com.poly.bookingapi.dto.ReservationSortRequest;
import com.poly.bookingapi.entity.DinnerTable;
import com.poly.bookingapi.proxydto.DinnerTableProxy;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DinnerTableService {

    List<DinnerTable> getAll();
    DinnerTable add(DinnerTableDTO dinnerTableDTO);
    DinnerTable update(DinnerTableDTO dinnerTableDTO,Integer id);
    DinnerTable delete(Integer id);
    DinnerTable detail(Integer id);

    List<DinnerTableProxy> getAllByDiningRoomId(Integer id, Integer idRoom);
    List<DinnerTableProxy> getAllByRoomId(Integer id);
}
