package com.poly.bookingapi.service.impl;

import com.poly.bookingapi.dto.ReservationDTO;
import com.poly.bookingapi.dto.ReservationPorductDTO;
import com.poly.bookingapi.entity.*;
import com.poly.bookingapi.repository.*;
import com.poly.bookingapi.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationProductRepository reservationProductRepository;

    @Autowired
    private DiningRoomRepository diningRoomRepository;

    @Autowired
    private DinnerTableRepository dinnerTableRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private VoucherRepository voucherRepository;


    @Override
    public List<ReservationDTO> getAll() {

//        List<ReservationDTO> getListDTO = getList.stream()
//                .map(reservationMap -> model
//                .map(reservationMap,ReservationDTO.class))
//                .collect(Collectors.toList());
//        return getListDTO;
        //Goi repo lay tu db
        List<Reservation> getList = reservationRepository.findAll();
        //Chuyển các entity thành các đối tượng Data Transfer Object(DTO) rồi trả về cho controller
        List<ReservationDTO> getListDto = new ArrayList<>();
        for (Reservation i: getList) {
             ReservationDTO dtos = i.loadData();
             getListDto.add(dtos);
        }
        return getListDto;
    }

    @Override
    public Reservation addByUser(ReservationDTO reservationDTO) {

//        Lay ra nguoi dung
//        Client client = clientRepository.getById(reservationDTO.getClient().getId());

        //luu vao reservation
        Reservation reservation = new Reservation();
//        reservation.setClient(client);
        reservation.setSdt(reservationDTO.getSdt());
        reservation.setNumberOfPeopleBooked(reservationDTO.getNumberOfPeopleBooked());
        reservation.setReservationDate(reservationDTO.getReservationDate());
        reservation.setCategoryDiningRoom(CategoryDiningRoom.builder().id(reservationDTO.getIdCategoryDiningRoom()).build());
//      reservation.setVoucher(Voucher.builder().id(reservationDTO.getIdVoucher())).build());
        reservation.setStatus(ReservationStatus.builder().id(reservationDTO.getIdStatus()).build());
        reservation.setStartTime(reservationDTO.getStartTime());
        reservation.setDelayTime(reservationDTO.getDelayTime());
        reservation.setEndTime(reservationDTO.getEndTime());
        reservation.setUpfrontPrice(reservationDTO.getUpfrontPrice());
        reservation.setCreatedAt(reservationDTO.getCreatedAt());
        reservationRepository.save(reservation);
        for (ReservationPorductDTO list : reservationDTO.getListProduct()) {
//            Product p = list.getProduct();
//            p.setId(list.getProduct().getId());
            ReservationProduct reservationProduct = new ReservationProduct();
            reservationProduct.setProduct(Product.builder().id(list.getProduct().getId()).build());
            reservationProduct.setQuantity(list.getQuantity());
            reservationProductRepository.save(reservationProduct);

        }
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation addByAdmin(ReservationDTO reservationDTO) {
        return null;
    }

    @Override
    public void addDiningRoom(CategoryDiningRoom categoryDiningRoom, Integer idRoom) {
        DiningRoom diningRoom = diningRoomRepository.
                findById(idRoom).
                orElse(null);
        if(diningRoom !=null){
            diningRoom.setCategory(categoryDiningRoom);
            diningRoomRepository.save(diningRoom);
        }
    }

    @Override
    public void addDinnerTable(DiningRoom diningRoom, Integer idTable) {
      DinnerTable dinnerTable = dinnerTableRepository.
                findById(idTable).
                orElse(null);
        if(dinnerTable != null){
            dinnerTable.setDiningRoom(diningRoom);
            dinnerTableRepository.save(dinnerTable);
        }
    }

    @Override
    public Reservation checkIn(ReservationDTO reservationDTO, Integer id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        return optionalReservation.map(resrvation ->{
            resrvation.setStatus(ReservationStatus.builder().id(reservationDTO.getIdStatus()).build());
            for (ReservationPorductDTO list : reservationDTO.getListProduct()){
                ReservationProduct reservationProduct = new ReservationProduct();
                reservationProduct.setProduct(Product.builder().id(list.getProduct().getId()).build());
                reservationProductRepository.save(reservationProduct);
            }
            return reservationRepository.save(resrvation);
        }).orElse(null);
    }

}
