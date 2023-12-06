package com.poly.bookingapi.service.impl;

import com.poly.bookingapi.dto.ProductDTO;
import com.poly.bookingapi.dto.ReservationAddDTO;
import com.poly.bookingapi.dto.ReservationViewDTO;
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
    private CategoryDiningRoomRepository categoryDiningRoomRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ReservationStatusRepository reservationStatusRepository;


    @Override
    public List<ReservationViewDTO> getAll() {

        //Goi repo lay tu db
        List<Reservation> getList = reservationRepository.findAll();
        //Chuyển các entity thành các đối tượng Data Transfer Object(DTO) rồi trả về cho controller
        List<ReservationViewDTO> getListDto = new ArrayList<>();
        for (Reservation i : getList) {
            ReservationViewDTO dtos = i.loadData();
            getListDto.add(dtos);
        }
        return getListDto;
    }

    @Override
    public String addByUser(ReservationAddDTO dto) {

        Reservation reservation = new Reservation();
        // check sdt đã đc đk chưa
        reservation.setSdt(dto.getSdt());
        reservation.setFullNameClient(dto.getFullname());
        reservation.setNumberOfPeopleBooked(dto.getNumberOfPeopleBooked());
        reservation.setReservationDate(dto.getDateTime().toLocalDate());
        reservation.setStartTime(dto.getDateTime().toLocalTime());
        // thời gian delay của khách mặc định cộng thêm 15p
        reservation.setDelayTime(dto.getDateTime().toLocalTime().plusMinutes(15));
        // thời gian kết thúc của khách dự trù là 2 tiếng
        reservation.setEndTime(dto.getDateTime().toLocalTime().plusHours(2));
        reservation.setCategoryDiningRoom(categoryDiningRoomRepository.findById(dto.getIdCategoryDiningRoom()).get());
        if(dto.getIdClient() != null && clientRepository.getBySDT(dto.getSdt()) == null) {
            Client client = clientRepository.getClientById(dto.getIdClient());
            reservation.setClient(client);
        } else if(dto.getIdClient() == null && clientRepository.getBySDT(dto.getSdt()) != null) {
            reservation.setClient(clientRepository.getBySDT(dto.getSdt()));
        }
        if(dto.getIdVoucher() != null) {
            Voucher voucher = voucherRepository.getById(dto.getIdVoucher());
            reservation.setVoucher(voucher);
        }
        reservation.setUpfrontPrice(dto.getUpfrontPrice());
        reservation.setOriginalPrice(dto.getOriginalPrice());
        reservation.setActualPrice(dto.getActualPrice());
        reservation.setPriceToPay(dto.getPriceToPay());
        reservation.setUpdateAt(LocalDate.now());
        reservation.setCreatedAt(LocalDate.now());
        reservation.setStatus(reservationStatusRepository.findById(1).get());
        Reservation reservationAdd = reservationRepository.save(reservation);

        if(dto.getListPorduct().size() != 0) {
            for (ProductDTO p: dto.getListPorduct()) {
                Product product = productRepository.getById(p.getId());
                ReservationProduct reservationProduct = new ReservationProduct();
                reservationProduct.setReservation(reservationAdd);
                reservationProduct.setProduct(product);
                reservationProduct.setNameProduct(p.getNameProduct());
                reservationProduct.setPrice(p.getPrice());
                reservationProduct.setQuantity(p.getQuantity());
                reservationProductRepository.save(reservationProduct);
            }
        }

        return "Tạo phiếu đặt thành công";
    }

    @Override
    public void addByAdmin(ReservationAddDTO reservationAddDTO) {

//        //luu vao reservation
//        Reservation reservation = new Reservation();
//        reservation.setSdt(reservationViewDTO.getSdt());
//        reservation.setNumberOfPeopleBooked(reservationViewDTO.getNumberOfPeopleBooked());
//        reservation.setReservationDate(reservationViewDTO.getReservationDate());
//        reservation.setCategoryDiningRoom(CategoryDiningRoom.builder().id(reservationViewDTO.getIdCategoryDiningRoom()).build());
//        reservation.setStatus(ReservationStatus.builder().id(1).build());
//        reservation.setStartTime(reservationViewDTO.getStartTime());
//        reservation.setDelayTime(reservationViewDTO.getDelayTime());
//        reservation.setEndTime(reservationViewDTO.getEndTime());
//        reservation.setUpfrontPrice(reservationViewDTO.getUpfrontPrice());
//        reservation.setCreatedAt(LocalDate.now());
//
//        long totalPrice = 0;
//        if (reservationViewDTO.getListProduct() != null) {
//            for (ProductDTO list : reservationViewDTO.getListProduct()) {
//                ReservationProduct reservationProduct = new ReservationProduct();
//                reservationProduct.setReservation(reservationRepository.save(reservation));
//                Product product = productRepository.getById(list.getId());
//                if (product.getNewPrice() != null) {
//                    reservationProduct.setProduct(product);
//                    reservationProduct.setNameProduct(product.getNameProduct());
//                    reservationProduct.setPrice(product.getNewPrice());
//                    reservationProduct.setQuantity(list.getQuantity());
//                    reservationProduct.setSubToTal(product.getNewPrice() * list.getQuantity());
//                    totalPrice += reservationProduct.getSubToTal();
//                } else {
//                    reservationProduct.setProduct(product);
//                    reservationProduct.setNameProduct(product.getNameProduct());
//                    reservationProduct.setPrice(product.getPrice());
//                    reservationProduct.setQuantity(list.getQuantity());
//                    reservationProduct.setSubToTal(product.getPrice() * list.getQuantity());
//                    totalPrice += reservationProduct.getSubToTal();
//                }
//
//                reservationProductRepository.save(reservationProduct);
//            }
//        }
//        reservation.setOriginalPrice(totalPrice);
//        reservationRepository.save(reservation);
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
    public void checkIn(ReservationAddDTO reservationAddDTO, Integer id) {
//        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Reservation"));
//        reservation.setStatus(ReservationStatus.builder().id(reservationViewDTO.getIdStatus()).build());
//        long totalPrice = 0;
//        if (reservationViewDTO.getListProduct() != null) {
//            for (ProductDTO list : reservationViewDTO.getListProduct()) {
//                ReservationProduct reservationProduct = new ReservationProduct();
//                reservationProduct.setReservation(reservationRepository.save(reservation));
//                Product product = productRepository.getById(list.getId());
//                if (product.getNewPrice() != null) {
//                    reservationProduct.setProduct(product);
//                    reservationProduct.setNameProduct(product.getNameProduct());
//                    reservationProduct.setPrice(product.getNewPrice());
//                    reservationProduct.setQuantity(list.getQuantity());
//                    reservationProduct.setSubToTal(product.getNewPrice() * list.getQuantity());
//                    totalPrice += reservationProduct.getSubToTal();
//                } else {
//                    reservationProduct.setProduct(product);
//                    reservationProduct.setNameProduct(product.getNameProduct());
//                    reservationProduct.setPrice(product.getPrice());
//                    reservationProduct.setQuantity(list.getQuantity());
//                    reservationProduct.setSubToTal(product.getPrice() * list.getQuantity());
//                    totalPrice += reservationProduct.getSubToTal();
//                }
//                reservationProductRepository.save(reservationProduct);
//            }
//        }
//        reservation.setOriginalPrice(totalPrice);
//        reservationRepository.save(reservation);
    }

    @Override
    public Optional<Reservation> detailReservation(Integer id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        return optionalReservation;
    }

    @Override
    public void updateByClient(ReservationAddDTO reservationAddDTO, Integer id) {
//        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Reservation"));
//        reservation.setStatus(ReservationStatus.builder().id(reservationViewDTO.getIdStatus()).build());
//        reservation.setFullNameClient(reservation.getFullNameClient());
//        reservation.setSdt(reservationViewDTO.getSdt());
//        reservation.setNumberOfPeopleBooked(reservationViewDTO.getNumberOfPeopleBooked());
//        reservation.setStartTime(reservationViewDTO.getStartTime());
//        reservation.setDelayTime(reservationViewDTO.getDelayTime());
//        reservation.setEndTime(reservationViewDTO.getEndTime());
//        reservation.setCategoryDiningRoom(CategoryDiningRoom.builder().id(reservationViewDTO.getIdCategoryDiningRoom()).build());
//        reservation.setCreatedAt(LocalDate.now());
//        long totalPrice = 0;
//        if (reservationViewDTO.getListProduct() != null) {
//            for (ProductDTO list : reservationViewDTO.getListProduct()) {
//                ReservationProduct reservationProduct = new ReservationProduct();
//                reservationProduct.setReservation(reservationRepository.save(reservation));
//                Product product = productRepository.getById(list.getId());
//                if (product.getNewPrice() != null) {
//                    reservationProduct.setProduct(product);
//                    reservationProduct.setNameProduct(product.getNameProduct());
//                    reservationProduct.setPrice(product.getNewPrice());
//                    reservationProduct.setQuantity(list.getQuantity());
//                    reservationProduct.setSubToTal(product.getNewPrice() * list.getQuantity());
//                    totalPrice += reservationProduct.getSubToTal();
//                } else {
//                    reservationProduct.setProduct(product);
//                    reservationProduct.setNameProduct(product.getNameProduct());
//                    reservationProduct.setPrice(product.getPrice());
//                    reservationProduct.setQuantity(list.getQuantity());
//                    reservationProduct.setSubToTal(product.getPrice() * list.getQuantity());
//                    totalPrice += reservationProduct.getSubToTal();
//                }
//                reservationProductRepository.save(reservationProduct);
//            }
//        }
//        reservation.setOriginalPrice(totalPrice);
//        reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getReservationByUser(Integer id) {
        List<Reservation> reservations = reservationRepository.getReservationByUser(id);
        return reservations;
    }


}
