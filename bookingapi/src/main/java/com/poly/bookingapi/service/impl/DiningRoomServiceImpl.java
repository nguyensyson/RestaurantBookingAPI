package com.poly.bookingapi.service.impl;

import com.poly.bookingapi.dto.DiningRoomDTO;
import com.poly.bookingapi.dto.DinnerTableDTO;
import com.poly.bookingapi.entity.CategoryDiningRoom;
import com.poly.bookingapi.entity.DiningRoom;
import com.poly.bookingapi.entity.DinnerTable;
import com.poly.bookingapi.repository.DiningRoomRepository;
import com.poly.bookingapi.repository.DinnerTableRepository;
import com.poly.bookingapi.respon.NotFoundException;
import com.poly.bookingapi.service.DiningRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DiningRoomServiceImpl implements DiningRoomService {

    @Autowired
    private DiningRoomRepository diningRoomRepository;

    @Autowired
    private DinnerTableRepository dinnerTableRepository;

    @Override
    public List<DiningRoom> getAll() { return diningRoomRepository.findAll(); }

    @Override
    public List<DiningRoom> getByIdCategory(Integer id){
        return diningRoomRepository.getByIdCategory(id);
    };

    @Override
    public DiningRoom add(DiningRoomDTO diningRoomDTO) {
        DiningRoom diningRoom = new DiningRoom();
        diningRoom.setCategory(CategoryDiningRoom.builder().id(diningRoomDTO.getIdCategoryDining()).build());
        diningRoom.setMaximumOccupancy(diningRoomDTO.getMaximumOccupancy());
        diningRoom.setNumberOfAvailable(diningRoomDTO.getMaximumOccupancy());
        List<DiningRoom> list = diningRoomRepository.findAll();
        diningRoom.setName("P" + String.format("%03d", list.size() + 1));
        diningRoom.setCreatedAt(LocalDate.now());
        diningRoom.setUpdateAt(LocalDate.now());
        return diningRoomRepository.save(diningRoom);
    }


    @Override
    public DiningRoom update(DiningRoomDTO diningRoomDTO, Integer id) {
        DiningRoom diningRoom = diningRoomRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found diningRoom"));
        diningRoom.setCategory(CategoryDiningRoom.builder().id(diningRoomDTO.getIdCategoryDining()).build());
        diningRoom.setMaximumOccupancy(diningRoomDTO.getMaximumOccupancy());
        diningRoom.setUpdateAt(LocalDate.now());
       return diningRoomRepository.save(diningRoom);
    }

    @Override
    public DiningRoom detail(Integer id) {return diningRoomRepository.findById(id).get();};

}
