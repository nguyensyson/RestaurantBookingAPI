package com.poly.bookingapi.service.impl;

import com.poly.bookingapi.dto.CategoryDiningRoomDTO;
import com.poly.bookingapi.entity.CategoryDiningRoom;
import com.poly.bookingapi.repository.CategoryDiningRoomRepository;
import com.poly.bookingapi.service.CategoryDiningRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryDiningRoomServiceImpl implements CategoryDiningRoomService {

    @Autowired
    private CategoryDiningRoomRepository repository;

    @Override
    public List<CategoryDiningRoomDTO> getALl() {
        List<CategoryDiningRoom> rooms = repository.findAll();
        List<CategoryDiningRoomDTO> roomDTOS = new ArrayList<>();
        for (CategoryDiningRoom c : rooms) {
            CategoryDiningRoomDTO dto = new CategoryDiningRoomDTO();
            dto.setId(c.getId());
            dto.setTitle(c.getTitle());
            roomDTOS.add(dto);
        }
        return roomDTOS;
    }
}
