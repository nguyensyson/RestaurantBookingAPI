package com.poly.bookingapi.service.impl;

import com.poly.bookingapi.dto.ProductDTO;
import com.poly.bookingapi.dto.ReservationDTO;
import com.poly.bookingapi.dto.ReservationPorductDTO;
import com.poly.bookingapi.entity.*;
import com.poly.bookingapi.repository.*;
import com.poly.bookingapi.respon.NotFoundException;
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

    @Autowired
    ProductRepository productRepository;


    @Override
    public List<ReservationDTO> getAll() {

        //Goi repo lay tu db
        List<Reservation> getList = reservationRepository.findAll();
        //Chuyển các entity thành các đối tượng Data Transfer Object(DTO) rồi trả về cho controller
        List<ReservationDTO> getListDto = new ArrayList<>();
        for (Reservation i : getList) {
            ReservationDTO dtos = i.loadData();
            getListDto.add(dtos);
        }
        return getListDto;
    }

    @Override
    public void addByUser(ReservationDTO reservationDTO) {

        //Lay ra nguoi dung
        Client client = clientRepository.getClientById(reservationDTO.getIdClient());
        //luu vao reservation
        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setFullNameClient(client.getFullname());
        reservation.setSdt(reservationDTO.getSdt());
        reservation.setNumberOfPeopleBooked(reservationDTO.getNumberOfPeopleBooked());
        reservation.setReservationDate(reservationDTO.getReservationDate());
        reservation.setCategoryDiningRoom(CategoryDiningRoom.builder().id(reservationDTO.getIdCategoryDiningRoom()).build());
        reservation.setStatus(ReservationStatus.builder().id(1).build());
        reservation.setStartTime(reservationDTO.getStartTime());
        reservation.setDelayTime(reservationDTO.getDelayTime());
        reservation.setEndTime(reservationDTO.getEndTime());
        reservation.setUpfrontPrice(reservationDTO.getUpfrontPrice());
        reservation.setCreatedAt(reservationDTO.getCreatedAt());
        long totalPrice = 0;
        if (reservationDTO.getListProduct() != null) {
            for (ProductDTO list : reservationDTO.getListProduct()) {
                ReservationProduct reservationProduct = new ReservationProduct();
                reservationProduct.setReservation(reservationRepository.save(reservation));
                reservationProduct.setProduct(Product.builder().id(list.getId()).build());
                reservationProduct.setNameProduct(list.getNameProduct());
                reservationProduct.setPrice(list.getPrice());
                reservationProduct.setQuantity(list.getQuantity());
                reservationProduct.setSubToTal(list.getPrice() * list.getQuantity());
                totalPrice +=reservationProduct.getSubToTal();
                reservationProductRepository.save(reservationProduct);
            }
        }
        reservation.setOriginalPrice(totalPrice);
        reservationRepository.save(reservation);
    }

    @Override
    public void addByAdmin(ReservationDTO reservationDTO) {

        //luu vao reservation
        Reservation reservation = new Reservation();
        reservation.setSdt(reservationDTO.getSdt());
        reservation.setNumberOfPeopleBooked(reservationDTO.getNumberOfPeopleBooked());
        reservation.setReservationDate(reservationDTO.getReservationDate());
        reservation.setCategoryDiningRoom(CategoryDiningRoom.builder().id(reservationDTO.getIdCategoryDiningRoom()).build());
        reservation.setStatus(ReservationStatus.builder().id(1).build());
        reservation.setStartTime(reservationDTO.getStartTime());
        reservation.setDelayTime(reservationDTO.getDelayTime());
        reservation.setEndTime(reservationDTO.getEndTime());
        reservation.setUpfrontPrice(reservationDTO.getUpfrontPrice());
        reservation.setCreatedAt(reservationDTO.getCreatedAt());
        long totalPrice = 0;
        if (reservationDTO.getListProduct() != null) {
            for (ProductDTO list : reservationDTO.getListProduct()) {
                ReservationProduct reservationProduct = new ReservationProduct();
                reservationProduct.setReservation(reservationRepository.save(reservation));
                reservationProduct.setProduct(Product.builder().id(list.getId()).build());
                reservationProduct.setNameProduct(list.getNameProduct());
                reservationProduct.setPrice(list.getPrice());
                reservationProduct.setQuantity(list.getQuantity());
                reservationProduct.setSubToTal(list.getPrice() * list.getQuantity());
                totalPrice +=reservationProduct.getSubToTal();
                reservationProductRepository.save(reservationProduct);
            }
        }
        reservation.setOriginalPrice(totalPrice);
        reservationRepository.save(reservation);
    }

    @Override
    public void addDiningRoom(CategoryDiningRoom categoryDiningRoom, Integer idRoom) {
        DiningRoom diningRoom = diningRoomRepository.
                findById(idRoom).
                orElse(null);
        if (diningRoom != null) {
            diningRoom.setCategory(categoryDiningRoom);
            diningRoomRepository.save(diningRoom);
        }
    }

    @Override
    public void addDinnerTable(DiningRoom diningRoom, Integer idTable) {
        DinnerTable dinnerTable = dinnerTableRepository.
                findById(idTable).
                orElse(null);
        if (dinnerTable != null) {
            dinnerTable.setDiningRoom(diningRoom);
            dinnerTableRepository.save(dinnerTable);
        }
    }

    @Override
    public Integer countReservation() {
        return reservationRepository.countReservation();
    }

    @Override
    public void checkIn(ReservationDTO reservationDTO, Integer id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Reservation"));
        reservation.setStatus(ReservationStatus.builder().id(reservationDTO.getIdStatus()).build());
        long totalPrice = 0;
        if (reservationDTO.getListProduct() != null) {
            for (ProductDTO list : reservationDTO.getListProduct()) {
                ReservationProduct reservationProduct = new ReservationProduct();
                reservationProduct.setReservation(reservationRepository.save(reservation));
                reservationProduct.setProduct(Product.builder().id(list.getId()).build());
                reservationProduct.setNameProduct(list.getNameProduct());
                reservationProduct.setPrice(list.getPrice());
                reservationProduct.setQuantity(list.getQuantity());
                reservationProduct.setSubToTal(list.getPrice() * list.getQuantity());
                totalPrice += reservationProduct.getSubToTal();
                reservationProductRepository.save(reservationProduct);
            }
        }
        reservation.setOriginalPrice(totalPrice);
        reservationRepository.save(reservation);
    }
}
