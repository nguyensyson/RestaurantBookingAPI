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
    public List<DinnerTable> getAll() {return dinnerTableRepository.getAll(); }

    @Override
    public DinnerTable detail(Integer id){
        return dinnerTableRepository.findById(id).get();
    };

    @Override
    public DinnerTable add(DinnerTableDTO dinnerTableDTO) {
        DinnerTable dinnerTable = new DinnerTable();
        dinnerTable.setDiningRoom(DiningRoom.builder().id(dinnerTableDTO.getIdRoom()).build());
        dinnerTable.setNumberOfSeats(dinnerTableDTO.getNumberOfSeats());
        List<DinnerTable> list = dinnerTableRepository.findAll();
        dinnerTable.setTableCode("B" + String.format("%03d", list.size() + 1));
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
     * @see com.poly.bookingapi.controller.DinnerTableController#getAllDinnerTable()
     *  <p>
     *      Lấy danh sách các bàn ăn thuộc DiningRoom có id truyền vào
     *      Sử dụng DinnerTableProxy để lấy thông tin DiningRoom của bàn ăn
     *      Trả về danh sách các bàn ăn
     *  </p>
     */
    @Override
    public List<DinnerTableProxy> getAllByDiningRoomId(Integer id, Integer idRoom) {
        return dinnerTableRepository.getAllDESC(id, idRoom);
    }

    @Override
    public List<DinnerTableProxy> getAllByRoomId(Integer id){
        return dinnerTableRepository.getAllByDiningRoomId(id);
    }
}
