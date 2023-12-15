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
    public List<DiningRoomDTO> getAll() {
        List<DiningRoom> list = diningRoomRepository.findAll();

        List<DiningRoomDTO> newList = new ArrayList<>();
        for (DiningRoom dining : list) {
            DiningRoomDTO diningRoomDTO = new DiningRoomDTO();
            DiningRoomDTO loadDining = dining.loadData(diningRoomDTO);
            newList.add(loadDining);
        }
        return newList;
    }

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
        diningRoom.setCreatedAt(LocalDate.now());
        diningRoom.setUpdateAt(LocalDate.now());
//        for (DinnerTableDTO list : diningRoomDTO.getListDinnerTable()) {
//            list.setDiningRoom(diningRoomRepository.save(diningRoom));
//            DinnerTable dinnerTable = dinnerTableRepository.getById(list.getId());
//            dinnerTable.setDiningRoom(list.getDiningRoom());
//            dinnerTableRepository.save(dinnerTable);
//        }
        return diningRoomRepository.save(diningRoom);
    }


    @Override
    public DiningRoom update(DiningRoomDTO diningRoomDTO, Integer id) {
        DiningRoom diningRoom = diningRoomRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found diningRoom"));
        diningRoom.setMaximumOccupancy(diningRoomDTO.getMaximumOccupancy());
        diningRoom.setNumberOfAvailable(diningRoomDTO.getMaximumOccupancy());
        diningRoom.setUpdateAt(LocalDate.now());
//        for (DinnerTableDTO list : diningRoomDTO.getListDinnerTable()) {
//            list.setDiningRoom(diningRoomRepository.save(diningRoom));
//            DinnerTable dinnerTable = dinnerTableRepository.getById(list.getId());
//            dinnerTable.setDiningRoom(list.getDiningRoom());
//            dinnerTableRepository.save(dinnerTable);
//        }
       return diningRoomRepository.save(diningRoom);
    }

}
