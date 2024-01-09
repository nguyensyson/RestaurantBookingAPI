package com.poly.bookingapi.service;

import com.poly.bookingapi.dto.DiningRoomDTO;
import com.poly.bookingapi.entity.DiningRoom;

import java.util.List;

public interface DiningRoomService {

    List<DiningRoom> getAll();
    DiningRoom add(DiningRoomDTO diningRoomDTO);
    DiningRoom update(DiningRoomDTO diningRoomDTO,Integer id);
    List<DiningRoom> getByIdCategory(Integer id);
    DiningRoom detail(Integer id);
}
