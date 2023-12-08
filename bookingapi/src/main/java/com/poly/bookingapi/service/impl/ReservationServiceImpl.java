package com.poly.bookingapi.service.impl;

import com.poly.bookingapi.dto.*;
import com.poly.bookingapi.entity.*;
import com.poly.bookingapi.repository.*;
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
    @Autowired
    private TableDetailRepository tableDetailRepository;


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
        if(dto.getIdClient() != null) {
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
        reservation.setStatus(reservationStatusRepository.findById(dto.getStatus()).get());
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
    public String addByAdmin(ReservationUpdateDTO dto) {

        Reservation reservation = new Reservation();
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
        if(clientRepository.getBySDT(dto.getSdt()) != null) {
            reservation.setClient(clientRepository.getBySDT(dto.getSdt()));
        }
        reservation.setUpfrontPrice(dto.getUpfrontPrice());
        reservation.setOriginalPrice(dto.getOriginalPrice());
        reservation.setActualPrice(dto.getActualPrice());
        reservation.setPriceToPay(dto.getPriceToPay());
        reservation.setUpdateAt(LocalDate.now());
        reservation.setCreatedAt(LocalDate.now());
        reservation.setStatus(reservationStatusRepository.findById(3).get());
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
        // set số chỗ còn trống trong phòng
        DiningRoom diningRoom = diningRoomRepository.findById(dto.getIdRoom()).get();
        if(diningRoom.getNumberOfAvailable() == 0) {
            diningRoom.setNumberOfAvailable(diningRoom.getMaximumOccupancy() - dto.getNumberOfPeopleBooked());
            diningRoomRepository.save(diningRoom);
        } else {
            diningRoom.setNumberOfAvailable(diningRoom.getNumberOfAvailable() - dto.getNumberOfPeopleBooked());
            diningRoomRepository.save(diningRoom);
        }
//dổi trạng thái table
        for (Integer i : dto.getIdTable()) {
            DinnerTable dinnerTable = dinnerTableRepository.findById(i).get();
            dinnerTable.setStatus(0);
            dinnerTableRepository.save(dinnerTable);
        }
        //set table detail
        for (Integer i : dto.getIdTable()) {
            TableDetail tableDetail = new TableDetail();
            tableDetail.setReservation(reservationAdd);
            tableDetail.setDinnerTable(dinnerTableRepository.findById(i).get());
            tableDetail.setDiningRoom(diningRoomRepository.findById(dto.getIdRoom()).get());
            tableDetail.setCreatedAt(LocalDate.now());
            tableDetail.setUpdateAt(LocalDate.now());
            tableDetailRepository.save(tableDetail);
        }

        return "Tạo phiếu đặt thành công";
    }

//    @Override
//    public void addDiningRoom(CategoryDiningRoom categoryDiningRoom, Integer idRoom) {
//        DiningRoom diningRoom = diningRoomRepository.
//                findById(idRoom).
//                orElse(null);
//        if (diningRoom != null) {
//            diningRoom.setCategory(categoryDiningRoom);
//            diningRoomRepository.save(diningRoom);
//        }
//    }
//
//    @Override
//    public void addDinnerTable(DiningRoom diningRoom, Integer idTable) {
//        DinnerTable dinnerTable = dinnerTableRepository.
//                findById(idTable).
//                orElse(null);
//        if (dinnerTable != null) {
//            dinnerTable.setDiningRoom(diningRoom);
//            dinnerTableRepository.save(dinnerTable);
//        }
//    }

//    @Override
//    public Integer countReservation() {
//        return reservationRepository.countReservation();
//    }

    @Override
    public  String changePlaces(ChangePlacesDTO dto, Integer idResercation){
        Reservation reservation = reservationRepository.findById(idResercation).get();
        //kiểm tra xem có đổi loại phòng ko
        int numberPeople = reservation.getNumberOfPeopleBooked();
        if(reservation.getCategoryDiningRoom().getId() != dto.getIdCategoryDiningRoom()){
            reservation.setCategoryDiningRoom(categoryDiningRoomRepository.findById(dto.getIdCategoryDiningRoom()).get());
            reservationRepository.save(reservation);
        }

        //kiểm tra có đổi phòng hay không
        List<TableDetail> oldTableDetail = tableDetailRepository.getByReservationId(idResercation);
        int count = 0;
        int idOldRoom = 0;
        for (TableDetail t : oldTableDetail) {
            if(t.getDiningRoom().getId() != dto.getIdRoom()) {
                count++;
                idOldRoom = t.getDiningRoom().getId();
            }
        }
        if(count != 0) {
            // set số chỗ còn trống trong phòng mới
            DiningRoom diningRoom = diningRoomRepository.findById(dto.getIdRoom()).get();
            diningRoom.setNumberOfAvailable(diningRoom.getNumberOfAvailable() - dto.getNumberOfPeopleBooked());
            diningRoomRepository.save(diningRoom);

            // set chỗ trống trong phòng cũ
            DiningRoom oldDiningRoom = diningRoomRepository.findById(idOldRoom).get();
            oldDiningRoom.setNumberOfAvailable(oldDiningRoom.getNumberOfAvailable() + numberPeople);
            diningRoomRepository.save(oldDiningRoom);
        }

        //kiểm tra xem có đổi bàn hay không
        List<Integer> listOldTable = new ArrayList<>();
        for (TableDetail t: oldTableDetail) {
            listOldTable.add(t.getDinnerTable().getId());
        }
        boolean areEqual = listOldTable.equals(dto.getIdTable());

        if(areEqual) {
            return "Check in thành công";
        }
        //cập nhật trạng thái cho các bàn cũ
        for (TableDetail t : oldTableDetail) {
            DinnerTable dinnerTable = dinnerTableRepository.findById(t.getDinnerTable().getId()).get();
            dinnerTable.setStatus(1);
            dinnerTableRepository.save(dinnerTable);
        }
        //đổi trạng thái cho bàn mới
        for (Integer i : dto.getIdTable()) {
            DinnerTable dinnerTable = dinnerTableRepository.findById(i).get();
            dinnerTable.setStatus(0);
            dinnerTableRepository.save(dinnerTable);
        }
        //xoá hết dữ liệu về bàn cũ
        for (TableDetail t : oldTableDetail) {
            tableDetailRepository.deleteById(t.getId());
        }
        //set table detail
        for (Integer i : dto.getIdTable()) {
            TableDetail tableDetail = new TableDetail();
            tableDetail.setReservation(reservationRepository.findById(idResercation).get());
            tableDetail.setDinnerTable(dinnerTableRepository.findById(i).get());
            tableDetail.setDiningRoom(diningRoomRepository.findById(dto.getIdRoom()).get());
            tableDetail.setCreatedAt(LocalDate.now());
            tableDetail.setUpdateAt(LocalDate.now());
            tableDetailRepository.save(tableDetail);
        }

        return "cập nhật thành công";
    };
    @Override
    public  String changeProduct(ChangeProductDTO dto, Integer idResercation){
        Reservation reservation = reservationRepository.findById(idResercation).get();
        for (ProductDTO p : dto.getListPorduct()) {
            if(reservationProductRepository.findByReservationAndProduct(reservation.getId(), p.getId()) != null) {
                ReservationProduct reservationProduct = reservationProductRepository.findByReservationAndProduct(reservation.getId(), p.getId());
                reservationProduct.setQuantity(p.getQuantity());
                reservationProductRepository.save(reservationProduct);
            } else {
                Product product = productRepository.getById(p.getId());
                ReservationProduct reservationProduct = new ReservationProduct();
                reservationProduct.setReservation(reservation);
                reservationProduct.setProduct(product);
                reservationProduct.setNameProduct(p.getNameProduct());
                reservationProduct.setPrice(p.getPrice());
                reservationProduct.setQuantity(p.getQuantity());
                reservationProductRepository.save(reservationProduct);
            }
        }

        reservation.setOriginalPrice(dto.getOriginalPrice());
        reservation.setActualPrice(dto.getActualPrice());
        reservation.setPriceToPay(dto.getPriceToPay());
        reservationRepository.save(reservation);
        return "Cập nhật thành công";
    };

    @Override
    public String arrangeSeats(ChangePlacesDTO dto, Integer idResercation){
        // set số chỗ còn trống trong phòng
        DiningRoom diningRoom = diningRoomRepository.findById(dto.getIdRoom()).get();
        if(diningRoom.getNumberOfAvailable() == 0) {
            diningRoom.setNumberOfAvailable(diningRoom.getMaximumOccupancy() - dto.getNumberOfPeopleBooked());
            diningRoomRepository.save(diningRoom);
        } else {
            diningRoom.setNumberOfAvailable(diningRoom.getNumberOfAvailable() - dto.getNumberOfPeopleBooked());
            diningRoomRepository.save(diningRoom);
        }
        //dổi trạng thái table
        for (Integer i : dto.getIdTable()) {
            DinnerTable dinnerTable = dinnerTableRepository.findById(i).get();
            dinnerTable.setStatus(0);
            dinnerTableRepository.save(dinnerTable);
        }
        //set table detail
        Reservation reservation = reservationRepository.findById(idResercation).get();
        for (Integer i : dto.getIdTable()) {
            TableDetail tableDetail = new TableDetail();
            tableDetail.setReservation(reservation);
            tableDetail.setDinnerTable(dinnerTableRepository.findById(i).get());
            tableDetail.setDiningRoom(diningRoomRepository.findById(dto.getIdRoom()).get());
            tableDetail.setCreatedAt(LocalDate.now());
            tableDetail.setUpdateAt(LocalDate.now());
            tableDetailRepository.save(tableDetail);
        }
        return "Cập nhật thành công";
    };

    @Override
    public String changeStatus(ChangeStatusDTO dto, Integer id) {
        Reservation reservation = reservationRepository.findById(id).get();
        reservation.setNumberOfPeopleBooked(dto.getNumberOfPeopleBooked());
        reservation.setStatus(reservationStatusRepository.findById(dto.getStatus()).get());
        reservationRepository.save(reservation);
        return "Cập nhật thành công";
    }

    @Override
    public Optional<Reservation> detailReservation(Integer id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        return optionalReservation;
    }

    @Override
    public String updateByClient(ReservationAddDTO dto, Integer id) {
        Reservation reservation = reservationRepository.findById(id).get();
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
        if(dto.getIdClient() != null) {
            Client client = clientRepository.getClientById(dto.getIdClient());
            reservation.setClient(client);
        } else if(dto.getIdClient() == null && clientRepository.getBySDT(dto.getSdt()) != null) {
            reservation.setClient(clientRepository.getBySDT(dto.getSdt()));
        }
        if(dto.getIdVoucher() != null) {
            Voucher voucher = voucherRepository.getById(dto.getIdVoucher());
            reservation.setVoucher(voucher);
        }
        reservation.setUpdateAt(LocalDate.now());
        reservation.setStatus(reservationStatusRepository.findById(dto.getStatus()).get());
        Reservation reservationAdd = reservationRepository.save(reservation);
        return "Cập nhật phiếu đặt thành công";
    }

    @Override
    public String updateByAdmin(ReservationUpdateDTO dto, Integer id) {
        Reservation reservation = reservationRepository.findById(id).get();
        reservation.setNumberOfPeopleBooked(dto.getNumberOfPeopleBooked());
        reservation.setReservationDate(dto.getDateTime().toLocalDate());
        reservation.setStartTime(dto.getDateTime().toLocalTime());
        // thời gian delay của khách mặc định cộng thêm 15p
        reservation.setDelayTime(dto.getDateTime().toLocalTime().plusMinutes(15));
        // thời gian kết thúc của khách dự trù là 2 tiếng
        reservation.setEndTime(dto.getDateTime().toLocalTime().plusHours(2));
        reservation.setUpdateAt(LocalDate.now());
        reservationRepository.save(reservation);
        return "Cập nhật thành công";
    }

    @Override
    public List<Reservation> getReservationByUser(Integer id) {
        List<Reservation> reservations = reservationRepository.getReservationByUser(id);
        return reservations;
    }
}
