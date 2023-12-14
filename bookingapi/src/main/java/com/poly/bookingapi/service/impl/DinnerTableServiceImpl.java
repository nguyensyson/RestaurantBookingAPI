package com.poly.bookingapi.service.impl;

import com.poly.bookingapi.dto.DinnerTableDTO;
import com.poly.bookingapi.dto.DinnerTableRequest;
import com.poly.bookingapi.dto.ReservationSortRequest;
import com.poly.bookingapi.dto.ReservationViewDTO;
import com.poly.bookingapi.entity.DiningRoom;
import com.poly.bookingapi.entity.DinnerTable;
import com.poly.bookingapi.entity.Reservation;
import com.poly.bookingapi.proxydto.DinnerTableProxy;
import com.poly.bookingapi.repository.DinnerTableRepository;
import com.poly.bookingapi.service.DinnerTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DinnerTableServiceImpl implements DinnerTableService {

    @Autowired
    private DinnerTableRepository dinnerTableRepository;

    @Override
    public Page<DinnerTableDTO> getAll(DinnerTableRequest model) {
        Pageable pageable = PageRequest.of(model.getPage(), model.getSize());
        Page<DinnerTable> list = dinnerTableRepository.findAll(pageable);
        //Chuyển các entity thành các đối tượng Data Transfer Object(DTO) rồi trả về cho controller
        List<DinnerTableDTO> viewDTOS = list.getContent().stream()
                .map(this::convertToDinnerTableDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(viewDTOS, list.getPageable(), list.getTotalElements());
    }

    private DinnerTableDTO convertToDinnerTableDTO(DinnerTable dinnerTable) {
        DinnerTableDTO dto = new DinnerTableDTO();
        dto.setId(dinnerTable.getId());
        dto.setStatus(dinnerTable.getStatus());
        dto.setTableCode(dinnerTable.getTableCode());
        dto.setNumberOfSeats(dinnerTable.getNumberOfSeats());
        return dto;
    }


    @Override
    public DinnerTable add(DinnerTableDTO dinnerTableDTO) {
        DinnerTable dinnerTable = new DinnerTable();
        dinnerTable.setDiningRoom(DiningRoom.builder().id(dinnerTableDTO.getIdRoom()).build());
        dinnerTable.setNumberOfSeats(dinnerTableDTO.getNumberOfSeats());
        dinnerTable.setStatus(1);
        dinnerTable.setCreatedAt(LocalDate.now());
        return dinnerTableRepository.save(dinnerTable);
    }

    @Override
    public DinnerTable update(DinnerTableDTO dinnerTableDTO, Integer id) {
        Optional<DinnerTable>optional = dinnerTableRepository.findById(id);
        return optional.map(dinnerTable -> {
            dinnerTable.setDiningRoom(DiningRoom.builder().id(dinnerTableDTO.getIdRoom()).build());
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

    /**
     * @param id id của DiningRoom
     * @return danh sách các bàn ăn thuộc DiningRoom có id truyền vào
     * @see com.poly.bookingapi.repository.DinnerTableRepository#getAllByDiningRoomId(Integer)
     * @see com.poly.bookingapi.proxydto.DinnerTableProxy
     * @see com.poly.bookingapi.controller.DinnerTableController#getAllDinnerTable(DinnerTableRequest)
     *  <p>
     *      Lấy danh sách các bàn ăn thuộc DiningRoom có id truyền vào
     *      Sử dụng DinnerTableProxy để lấy thông tin DiningRoom của bàn ăn
     *      Trả về danh sách các bàn ăn
     *  </p>
     */
    @Override
    public List<DinnerTableProxy> getAllByDiningRoomId(Integer id) {
        return dinnerTableRepository.getAllByDiningRoomId(id);
    }
}
