package com.poly.bookingapi.service.impl;

import com.poly.bookingapi.dto.*;
import com.poly.bookingapi.entity.*;
import com.poly.bookingapi.repository.*;
import com.poly.bookingapi.service.ReservationService;
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
    public Page<ReservationViewDTO> getAll(ReservationSortRequest model) {
        //Goi repo lay tu db
        Pageable pageable = PageRequest.of(model.getPage(), model.getSize());
        Page<Reservation> getList = reservationRepository.getAll(pageable);
        //Chuyển các entity thành các đối tượng Data Transfer Object(DTO) rồi trả về cho controller
        List<ReservationViewDTO> viewDTOS = getList.getContent().stream()
            .map(this::convertToReservationViewDTO)
            .collect(Collectors.toList());

        return new PageImpl<>(viewDTOS, getList.getPageable(), getList.getTotalElements());
    }

    @Override
    public Page<ReservationViewDTO> getByStatus(ReservationSortRequest model) {
        Pageable pageable = PageRequest.of(model.getPage(), model.getSize());
        Page<Reservation> getList = reservationRepository.getByStatus(model.getStatusID(), pageable);
        //Chuyển các entity thành các đối tượng Data Transfer Object(DTO) rồi trả về cho controller
        List<ReservationViewDTO> viewDTOS = getList.getContent().stream()
            .map(this::convertToReservationViewDTO)
            .collect(Collectors.toList());

        return new PageImpl<>(viewDTOS, getList.getPageable(), getList.getTotalElements());
    }

    ;

    private ReservationViewDTO convertToReservationViewDTO(Reservation reservation) {
        ReservationViewDTO viewDTO = new ReservationViewDTO();
        viewDTO.setId(reservation.getId());
        if(reservation.getReservationDate() != null) {
            viewDTO.setReservationDate(reservation.getReservationDate().atTime(reservation.getStartTime()));
        }
        viewDTO.setCreatedAt(reservation.getCreatedAt());
        viewDTO.setSdt(reservation.getSdt());
        viewDTO.setFullname(reservation.getFullNameClient());
        if (reservation.getStatus().getId() != null) {
            viewDTO.setOderStatus(reservation.getStatus());
        }
        if (reservation.getCategoryDiningRoom().getId() != null) {
            viewDTO.setIdCategoryDiningRoom(reservation.getCategoryDiningRoom().getId());
        }
        return viewDTO;
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
        if (dto.getIdCategoryDiningRoom() != null) {
            reservation.setCategoryDiningRoom(categoryDiningRoomRepository.findById(dto.getIdCategoryDiningRoom()).get());
        }
//        if(dto.getIdClient() != null) {
//            Client client = clientRepository.getClientById(dto.getIdClient());
//            reservation.setClient(client);
//        } else if(dto.getIdClient() == null && clientRepository.getBySDT(dto.getSdt()) != null) {
        if (clientRepository.getBySDT(dto.getSdt()) != null) {
            reservation.setClient(clientRepository.getBySDT(dto.getSdt()));
        }
//        }
//        if(dto.getIdVoucher() != null) {
//            Voucher voucher = voucherRepository.getById(dto.getIdVoucher());
//            reservation.setVoucher(voucher);
//        }
        if (dto.getTitleVoucher() != null && voucherRepository.getByTitle(dto.getTitleVoucher()) != null) {
            Voucher voucher = voucherRepository.getByTitle(dto.getTitleVoucher());
            reservation.setVoucher(voucher);
            reservation.setActualPrice((dto.getOriginalPrice() * voucher.getVoucherValue()) / 100);
        }

        long price = 0;
        if (dto.getListPorduct().size() != 0) {
            for (ProductDTO p : dto.getListPorduct()) {
                price = price + (p.getPrice()*p.getQuantity());
            }
        }
        reservation.setUpfrontPrice(Long.valueOf(0));
        reservation.setOriginalPrice(price);
        reservation.setActualPrice(price);
        reservation.setPriceToPay(price);
        reservation.setUpdateAt(LocalDate.now());
        reservation.setCreatedAt(LocalDate.now());
        reservation.setStatus(reservationStatusRepository.findById(dto.getStatus()).get());
        Reservation reservationAdd = reservationRepository.save(reservation);

        if (dto.getListPorduct().size() != 0) {
            for (ProductDTO p : dto.getListPorduct()) {
                Product product = productRepository.getById(p.getId());
                ReservationProduct reservationProduct = new ReservationProduct();
                reservationProduct.setReservation(reservationAdd);
                reservationProduct.setProduct(product);
                reservationProduct.setAvatarProduct(product.getAvatar());
                reservationProduct.setNameProduct(product.getNameProduct());
                reservationProduct.setNameProduct(p.getNameProduct());
                reservationProduct.setPrice(p.getPrice());
                reservationProduct.setQuantity(p.getQuantity());
                reservationProduct.setSubToTal(p.getPrice() * p.getQuantity());
                reservationProduct.setCreatedAt(LocalDate.now());
                reservationProduct.setUpdateAt(LocalDate.now());
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
        if(dto.getDateTime() != null) {
            reservation.setReservationDate(dto.getDateTime().toLocalDate());
            reservation.setStartTime(dto.getDateTime().toLocalTime());
            // thời gian delay của khách mặc định cộng thêm 15p
            reservation.setDelayTime(dto.getDateTime().toLocalTime().plusMinutes(15));
            // thời gian kết thúc của khách dự trù là 2 tiếng
            reservation.setEndTime(dto.getDateTime().toLocalTime().plusHours(2));
        }

        if(dto.getIdCategoryDiningRoom() != null) {
            reservation.setCategoryDiningRoom(categoryDiningRoomRepository.findById(dto.getIdCategoryDiningRoom()).get());
        }
        if (clientRepository.getBySDT(dto.getSdt()) != null) {
            reservation.setClient(clientRepository.getBySDT(dto.getSdt()));
        }
        long price = 0;
        if (dto.getListPorduct().size() != 0) {
            for (ProductDTO p : dto.getListPorduct()) {
                price = price + (p.getPrice()*p.getQuantity());
            }
        }
        reservation.setUpfrontPrice(Long.valueOf(0));
        reservation.setOriginalPrice(price);
        reservation.setActualPrice(price);
        reservation.setPriceToPay(price);
        reservation.setUpdateAt(LocalDate.now());
        reservation.setCreatedAt(LocalDate.now());
        reservation.setStatus(reservationStatusRepository.findById(2).get());
        Reservation reservationAdd = reservationRepository.save(reservation);

        if (dto.getListPorduct().size() != 0) {
            for (ProductDTO p : dto.getListPorduct()) {
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
        if (diningRoom.getNumberOfAvailable() == 0) {
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
    public String changePlaces(ChangePlacesDTO dto, Integer idResercation) {
        Reservation reservation = reservationRepository.findById(idResercation).get();
        //kiểm tra xem có đổi loại phòng ko
        int numberPeople = reservation.getNumberOfPeopleBooked();
        if (reservation.getCategoryDiningRoom().getId() != dto.getIdCategoryDiningRoom()) {
            reservation.setCategoryDiningRoom(categoryDiningRoomRepository.findById(dto.getIdCategoryDiningRoom()).get());
            reservationRepository.save(reservation);
        }

        //kiểm tra có đổi phòng hay không
        List<TableDetail> oldTableDetail = tableDetailRepository.getByReservationId(idResercation);
        int count = 0;
        int idOldRoom = 0;
        for (TableDetail t : oldTableDetail) {
            if (t.getDiningRoom().getId() != dto.getIdRoom()) {
                count++;
                idOldRoom = t.getDiningRoom().getId();
            }
        }
        if (count != 0) {
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
        for (TableDetail t : oldTableDetail) {
            listOldTable.add(t.getDinnerTable().getId());
        }
        boolean areEqual = listOldTable.equals(dto.getIdTable());

        if (areEqual) {
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
    }

    ;

    @Override
    public String changeProduct(ChangeProductDTO dto, Integer idResercation) {
        Reservation reservation = reservationRepository.findById(idResercation).get();
        long price = 0;
        for (ProductDTO p : dto.getListPorduct()) {

            System.out.print(p.getId() + " " + p.getNameProduct() + " " + p.getQuantity() + " ");
            if (reservationProductRepository.findByReservationAndProduct(reservation.getId(), p.getId()) != null) {
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

            Product product = productRepository.getById(p.getId());
            price = price + (product.getPrice()*p.getQuantity());
        }

        reservation.setOriginalPrice(price);
        reservation.setPriceToPay(price);
        reservation.setActualPrice(price);
        reservationRepository.save(reservation);
        return "Cập nhật thành công";
    };

    @Override
    public String arrangeSeats(ChangePlacesDTO dto, Integer idResercation) {
        // set số chỗ còn trống trong phòng
        DiningRoom diningRoom = diningRoomRepository.findById(dto.getIdRoom()).get();
        if (diningRoom.getNumberOfAvailable() == 0) {
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
    }

    ;

    @Override
    public String changeStatus(ChangeStatusDTO dto, Integer id) {
        Reservation reservation = reservationRepository.findById(id).get();
        reservation.setNumberOfPeopleBooked(dto.getNumberOfPeopleBooked());
        reservation.setStatus(reservationStatusRepository.findById(dto.getStatus()).get());
        reservationRepository.save(reservation);
        return "Cập nhật thành công";
    }

    @Override
    public ReservationViewDTO detailReservation(Integer id) {
        Reservation optionalReservation = reservationRepository.findById(id).get();
        ReservationViewDTO dto = new ReservationViewDTO();
        dto.setId(optionalReservation.getId());
        dto.setSdt(optionalReservation.getSdt());
        dto.setFullname(optionalReservation.getFullNameClient());
        dto.setIdCategoryDiningRoom(optionalReservation.getCategoryDiningRoom().getId());
        dto.setOderStatus(optionalReservation.getStatus());
        dto.setNumberOfPeopleBooked(optionalReservation.getNumberOfPeopleBooked());
        if(optionalReservation.getReservationDate() != null) {
            dto.setReservationDate(optionalReservation.getReservationDate().atTime(optionalReservation.getStartTime()));
        }

        List<DiningRoom> rooms = diningRoomRepository.getAllDESC(id, optionalReservation.getCategoryDiningRoom().getId());
        dto.setDiningRoom(rooms);
        dto.setDinnerTables(dinnerTableRepository.getAllDESC(id, rooms.get(0).getId()));
        dto.setUpfrontPrice(optionalReservation.getUpfrontPrice());
        dto.setOriginalPrice(optionalReservation.getOriginalPrice());
        dto.setActualPrice(optionalReservation.getActualPrice());
        dto.setPriceToPay(optionalReservation.getPriceToPay());
        return dto;
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
//        if(dto.getIdClient() != null) {
//            Client client = clientRepository.getClientById(dto.getIdClient());
//            reservation.setClient(client);
//        } else if(dto.getIdClient() == null && clientRepository.getBySDT(dto.getSdt()) != null) {
//            reservation.setClient(clientRepository.getBySDT(dto.getSdt()));
//        }
//        if(dto.getIdVoucher() != null) {
//            Voucher voucher = voucherRepository.getById(dto.getIdVoucher());
//            reservation.setVoucher(voucher);
//        }
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
    public Page<ReservationViewDTO> getReservationByUser(Integer id, ReservationSortRequest model) {
        Pageable pageable = PageRequest.of(model.getPage(), model.getSize());
        Page<Reservation> getList = reservationRepository.getReservationByUser(id, pageable);
        //Chuyển các entity thành các đối tượng Data Transfer Object(DTO) rồi trả về cho controller
        List<ReservationViewDTO> viewDTOS = getList.getContent().stream()
            .map(this::convertToReservationViewDTO)
            .collect(Collectors.toList());

        return new PageImpl<>(viewDTOS, getList.getPageable(), getList.getTotalElements());
    }

    @Override
    public Invoice invoice(Integer id){
        Invoice invoice = new Invoice();
        Reservation optionalReservation = reservationRepository.findById(id).orElseThrow();
        invoice.setId(optionalReservation.getId());
        invoice.setSdt(optionalReservation.getSdt());
        invoice.setFullname(optionalReservation.getFullNameClient());
        invoice.setCategoryDiningRoom(optionalReservation.getCategoryDiningRoom().getTitle());
//        invoice.setOderStatus(optionalReservation.getStatus());
        invoice.setNumberOfPeopleBooked(optionalReservation.getNumberOfPeopleBooked());
        if(optionalReservation.getReservationDate() != null) {
            invoice.setReservationDate(optionalReservation.getReservationDate().atTime(optionalReservation.getStartTime()));
        }
        if(diningRoomRepository.getByReservation(id) != null) {
            invoice.setDiningRoom(diningRoomRepository.getByReservation(id).getName());
        }
        if(dinnerTableRepository.getByReservation(id).size() != 0) {
            List<DinnerTable> list = dinnerTableRepository.getByReservation(id);
            String table = "";
            for (int i = 0; i < list.size(); i++) {
                table += list.get(i).getTableCode();

                if (i < list.size() - 1) {
                    table += ", ";
                }
            }
            invoice.setDiningTable(table);
        }
        invoice.setUpfrontPrice(optionalReservation.getUpfrontPrice());
        invoice.setOriginalPrice(optionalReservation.getOriginalPrice());
        invoice.setActualPrice(optionalReservation.getActualPrice());
        invoice.setPriceToPay(optionalReservation.getPriceToPay());
        if(productRepository.getByReservation(id) != null) {
            invoice.setProducts(productRepository.getByReservation(id));
        }
        System.out.print("oke");
        return invoice;
    };

}
