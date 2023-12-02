package com.poly.bookingapi.entity;

import com.poly.bookingapi.dto.ReservationDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @Column(name = "fullname_client")
    private String fullNameClient;

    @Column(name = "sdt")
    private String sdt;

    @Column(name = "number_of_people_booked")
    private Integer numberOfPeopleBooked;

    @Column(name = "reservation_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate reservationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_dining_room_id", referencedColumnName = "id")
    private CategoryDiningRoom categoryDiningRoom;

    @Column(name = "start_time")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @Column(name = "end_time")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;

    @Column(name = "delay_time")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime delayTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private ReservationStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voucher_id", referencedColumnName = "id")
    private Voucher voucher;

    @Column(name = "upfront_price")
    private Long upfrontPrice;

    @Column(name = "original_price")
    private Long originalPrice;

    @Column(name = "actual_price")
    private Long actualPrice;

    @Column(name = "price_to_pay")
    private Long priceToPay;

    @Column(name = "created_at")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate createdAt;

    @Column(name = "update_at")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate updateAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", referencedColumnName = "id")
    private Admin updatedBy;

    @OneToMany(mappedBy = "reservation", fetch = FetchType.LAZY)
    private List<ReservationProduct> listReservationProduct;

    @OneToMany(mappedBy = "reservation", fetch = FetchType.LAZY)
    private List<PaymentDetail> listPaymentDetail;

    public ReservationDTO loadData(){
        ReservationDTO reservation = new ReservationDTO();
        reservation.setSdt(sdt);
        reservation.setNumberOfPeopleBooked(numberOfPeopleBooked);
        reservation.setReservationDate(reservationDate);
//        reservation.setIdCategoryDiningRoom(categoryDiningRoom.getId());
//        reservation.setIdStatus(status.getId());
//        reservation.setIdClient(client.getId());
//        reservation.setIdVoucher(voucher.getId());
//        reservation.setIdAdmin(updatedBy.getId());
        reservation.setStartTime(startTime);
        reservation.setEndTime(endTime);
        reservation.setDelayTime(delayTime);
        reservation.setUpfrontPrice(upfrontPrice);
        reservation.setCreatedAt(createdAt);
        return reservation;
    }
}
