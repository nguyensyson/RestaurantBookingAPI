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

import java.time.LocalDate;
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

        //luu vao reservation
        if(reservationDTO.getIdVoucher() != null && reservationDTO.getIdClient() !=null){
            //lay vocher
            Voucher voucher = voucherRepository.getById(reservationDTO.getIdVoucher());
            //lay client
            Client client = clientRepository.getClientById(reservationDTO.getIdClient());
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
            reservation.setCreatedAt(LocalDate.now());
            long totalPrice = 0;
            if (reservationDTO.getListProduct() != null) {
                for (ProductDTO list : reservationDTO.getListProduct()) {
                    ReservationProduct reservationProduct = new ReservationProduct();
                    reservationProduct.setReservation(reservationRepository.save(reservation));
                    Product product = productRepository.getById(list.getId());
                    if (product.getNewPrice() != null) {
                        reservationProduct.setProduct(product);
                        reservationProduct.setNameProduct(product.getNameProduct());
                        reservationProduct.setPrice(product.getNewPrice());
                        reservationProduct.setQuantity(list.getQuantity());
                        reservationProduct.setSubToTal(product.getNewPrice() * list.getQuantity());
                        totalPrice += reservationProduct.getSubToTal();
                    } else {
                        reservationProduct.setProduct(product);
                        reservationProduct.setNameProduct(product.getNameProduct());
                        reservationProduct.setPrice(product.getPrice());
                        reservationProduct.setQuantity(list.getQuantity());
                        reservationProduct.setSubToTal(product.getPrice() * list.getQuantity());
                        totalPrice += reservationProduct.getSubToTal();
                    }
                    reservationProductRepository.save(reservationProduct);
                }
            }
            reservation.setVoucher(voucher);
            reservation.setOriginalPrice(totalPrice - ((totalPrice * reservation.getVoucher().getVoucherValue()) / 100));
            reservationRepository.save(reservation);
        } else if(reservationDTO.getIdVoucher() == null && reservationDTO.getIdClient() != null){
            Reservation reservation = new Reservation();
            Client client = clientRepository.getClientById(reservationDTO.getIdClient());
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
            reservation.setCreatedAt(LocalDate.now());
            long totalPrice = 0;
            if (reservationDTO.getListProduct() != null) {
                for (ProductDTO list : reservationDTO.getListProduct()) {
                    ReservationProduct reservationProduct = new ReservationProduct();
                    reservationProduct.setReservation(reservationRepository.save(reservation));
                    Product product = productRepository.getById(list.getId());
                    if (product.getNewPrice() != null) {
                        reservationProduct.setProduct(product);
                        reservationProduct.setNameProduct(product.getNameProduct());
                        reservationProduct.setPrice(product.getNewPrice());
                        reservationProduct.setQuantity(list.getQuantity());
                        reservationProduct.setSubToTal(product.getNewPrice() * list.getQuantity());
                        totalPrice += reservationProduct.getSubToTal();
                    } else {
                        reservationProduct.setProduct(product);
                        reservationProduct.setNameProduct(product.getNameProduct());
                        reservationProduct.setPrice(product.getPrice());
                        reservationProduct.setQuantity(list.getQuantity());
                        reservationProduct.setSubToTal(product.getPrice() * list.getQuantity());
                        totalPrice += reservationProduct.getSubToTal();
                    }
                    reservationProductRepository.save(reservationProduct);
                }
            }
            reservation.setVoucher(null);
            reservation.setOriginalPrice(totalPrice);
            reservationRepository.save(reservation);
        }else{
            Reservation reservation = new Reservation();
            reservation.setClient(null);
            reservation.setFullNameClient(null);
            reservation.setSdt(reservationDTO.getSdt());
            reservation.setNumberOfPeopleBooked(reservationDTO.getNumberOfPeopleBooked());
            reservation.setReservationDate(reservationDTO.getReservationDate());
            reservation.setCategoryDiningRoom(CategoryDiningRoom.builder().id(reservationDTO.getIdCategoryDiningRoom()).build());
            reservation.setStatus(ReservationStatus.builder().id(1).build());
            reservation.setStartTime(reservationDTO.getStartTime());
            reservation.setDelayTime(reservationDTO.getDelayTime());
            reservation.setEndTime(reservationDTO.getEndTime());
            reservation.setUpfrontPrice(reservationDTO.getUpfrontPrice());
            reservation.setCreatedAt(LocalDate.now());
            long totalPrice = 0;
            if (reservationDTO.getListProduct() != null) {
                for (ProductDTO list : reservationDTO.getListProduct()) {
                    ReservationProduct reservationProduct = new ReservationProduct();
                    reservationProduct.setReservation(reservationRepository.save(reservation));
                    Product product = productRepository.getById(list.getId());
                    if (product.getNewPrice() != null) {
                        reservationProduct.setProduct(product);
                        reservationProduct.setNameProduct(product.getNameProduct());
                        reservationProduct.setPrice(product.getNewPrice());
                        reservationProduct.setQuantity(list.getQuantity());
                        reservationProduct.setSubToTal(product.getNewPrice() * list.getQuantity());
                        totalPrice += reservationProduct.getSubToTal();
                    } else {
                        reservationProduct.setProduct(product);
                        reservationProduct.setNameProduct(product.getNameProduct());
                        reservationProduct.setPrice(product.getPrice());
                        reservationProduct.setQuantity(list.getQuantity());
                        reservationProduct.setSubToTal(product.getPrice() * list.getQuantity());
                        totalPrice += reservationProduct.getSubToTal();
                    }
                    reservationProductRepository.save(reservationProduct);
                }
            }
            reservation.setVoucher(null);
            reservation.setOriginalPrice(totalPrice);
            reservationRepository.save(reservation);
        }
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
        reservation.setCreatedAt(LocalDate.now());
        long totalPrice = 0;
        if (reservationDTO.getListProduct() != null) {
            for (ProductDTO list : reservationDTO.getListProduct()) {
                ReservationProduct reservationProduct = new ReservationProduct();
                reservationProduct.setReservation(reservationRepository.save(reservation));
                Product product = productRepository.getById(list.getId());
                if (product.getNewPrice() != null) {
                    reservationProduct.setProduct(product);
                    reservationProduct.setNameProduct(product.getNameProduct());
                    reservationProduct.setPrice(product.getNewPrice());
                    reservationProduct.setQuantity(list.getQuantity());
                    reservationProduct.setSubToTal(product.getNewPrice() * list.getQuantity());
                    totalPrice += reservationProduct.getSubToTal();
                } else {
                    reservationProduct.setProduct(product);
                    reservationProduct.setNameProduct(product.getNameProduct());
                    reservationProduct.setPrice(product.getPrice());
                    reservationProduct.setQuantity(list.getQuantity());
                    reservationProduct.setSubToTal(product.getPrice() * list.getQuantity());
                    totalPrice += reservationProduct.getSubToTal();
                }
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
                Product product = productRepository.getById(list.getId());
                if (product.getNewPrice() != null) {
                    reservationProduct.setProduct(product);
                    reservationProduct.setNameProduct(product.getNameProduct());
                    reservationProduct.setPrice(product.getNewPrice());
                    reservationProduct.setQuantity(list.getQuantity());
                    reservationProduct.setSubToTal(product.getNewPrice() * list.getQuantity());
                    totalPrice += reservationProduct.getSubToTal();
                } else {
                    reservationProduct.setProduct(product);
                    reservationProduct.setNameProduct(product.getNameProduct());
                    reservationProduct.setPrice(product.getPrice());
                    reservationProduct.setQuantity(list.getQuantity());
                    reservationProduct.setSubToTal(product.getPrice() * list.getQuantity());
                    totalPrice += reservationProduct.getSubToTal();
                }
                reservationProductRepository.save(reservationProduct);
            }
        }
        reservation.setOriginalPrice(totalPrice);
        reservationRepository.save(reservation);
    }

    @Override
    public Optional<Reservation> detailReservation(Integer id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        return optionalReservation;
    }

    @Override
    public void updateByClient(ReservationDTO reservationDTO, Integer id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Reservation"));
        reservation.setStatus(ReservationStatus.builder().id(reservationDTO.getIdStatus()).build());
        reservation.setFullNameClient(reservation.getFullNameClient());
        reservation.setSdt(reservationDTO.getSdt());
        reservation.setNumberOfPeopleBooked(reservationDTO.getNumberOfPeopleBooked());
        reservation.setStartTime(reservationDTO.getStartTime());
        reservation.setDelayTime(reservationDTO.getDelayTime());
        reservation.setEndTime(reservationDTO.getEndTime());
        reservation.setCategoryDiningRoom(CategoryDiningRoom.builder().id(reservationDTO.getIdCategoryDiningRoom()).build());
        reservation.setCreatedAt(LocalDate.now());
        long totalPrice = 0;
        if (reservationDTO.getListProduct() != null) {
            for (ProductDTO list : reservationDTO.getListProduct()) {
                ReservationProduct reservationProduct = new ReservationProduct();
                reservationProduct.setReservation(reservationRepository.save(reservation));
                Product product = productRepository.getById(list.getId());
                if (product.getNewPrice() != null) {
                    reservationProduct.setProduct(product);
                    reservationProduct.setNameProduct(product.getNameProduct());
                    reservationProduct.setPrice(product.getNewPrice());
                    reservationProduct.setQuantity(list.getQuantity());
                    reservationProduct.setSubToTal(product.getNewPrice() * list.getQuantity());
                    totalPrice += reservationProduct.getSubToTal();
                } else {
                    reservationProduct.setProduct(product);
                    reservationProduct.setNameProduct(product.getNameProduct());
                    reservationProduct.setPrice(product.getPrice());
                    reservationProduct.setQuantity(list.getQuantity());
                    reservationProduct.setSubToTal(product.getPrice() * list.getQuantity());
                    totalPrice += reservationProduct.getSubToTal();
                }
                reservationProductRepository.save(reservationProduct);
            }
        }
        reservation.setOriginalPrice(totalPrice);
        reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getReservationByUser(Integer id) {
        List<Reservation> reservations = reservationRepository.getReservationByUser(id);
        return reservations;
    }


}
