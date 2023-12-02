package com.poly.bookingapi.service.impl;

import com.poly.bookingapi.dto.DinnerTableDTO;
import com.poly.bookingapi.entity.DiningRoom;
import com.poly.bookingapi.entity.DinnerTable;
import com.poly.bookingapi.repository.DinnerTableRepository;
import com.poly.bookingapi.service.DinnerTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DinnerTableServiceImpl implements DinnerTableService {
    @Autowired
    private DinnerTableRepository dinnerTableRepository;

    @Override
    public List<DinnerTableDTO> getAll() {
        List<DinnerTable>list = dinnerTableRepository.findAll();

        List<DinnerTableDTO>listDTO = new ArrayList<>();
        for (DinnerTable i: list) {
            DinnerTableDTO dinnerTableDTO = new DinnerTableDTO();
            DinnerTableDTO add = i.loadData(dinnerTableDTO);
            listDTO.add(add);
        }
        return listDTO;
    }

    @Override
    public DinnerTable add(DinnerTableDTO dinnerTableDTO) {
        DinnerTable dinnerTable = new DinnerTable();
        dinnerTable.setDiningRoom(DiningRoom.builder().id(dinnerTableDTO.getDiningRoom().getId()).build());
        dinnerTable.setNumberOfSeats(dinnerTableDTO.getNumberOfSeats());
        dinnerTable.setStatus(1);
        dinnerTable.setCreatedAt(LocalDate.now());
        return dinnerTableRepository.save(dinnerTable);
    }

    @Override
    public DinnerTable update(DinnerTableDTO dinnerTableDTO, Integer id) {
        Optional<DinnerTable>optional = dinnerTableRepository.findById(id);
        return optional.map(dinnerTable -> {
            dinnerTable.setDiningRoom(DiningRoom.builder().id(dinnerTableDTO.getDiningRoom().getId()).build());
            dinnerTable.setNumberOfSeats(dinnerTableDTO.getNumberOfSeats());
            dinnerTable.setStatus(dinnerTableDTO.getStatus());
            dinnerTable.setUpdateAt(LocalDate.now());
            return dinnerTableRepository.save(dinnerTable);
        }).orElse(null);
    }

    @Override
    public DinnerTable delete(Integer id) {
        Optional<DinnerTable>optional = dinnerTableRepository.findById(id);
        return optional.map(dinnerTable -> {
            dinnerTable.setDiningRoom(null);
            dinnerTableRepository.delete(dinnerTable);
            return dinnerTable;
        }).orElse(null);
    }
}
