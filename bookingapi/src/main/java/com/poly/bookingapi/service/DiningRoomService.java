package com.poly.bookingapi.service;

import com.poly.bookingapi.dto.DiningRoomDTO;
import com.poly.bookingapi.entity.DiningRoom;

import java.util.List;

public interface DiningRoomService {
    List<DiningRoomDTO> getAll();
    DiningRoom add(DiningRoomDTO diningRoomDTO);
    void update(DiningRoomDTO diningRoomDTO,Integer id);
    DiningRoom delete(Integer id);
}