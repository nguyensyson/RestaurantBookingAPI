package com.poly.bookingapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "booking")
public class Booking {
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
    @Column(name = "booking_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate bookingDate;
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
    private BookingStatus status;
    @Column(name = "created_at")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate createdAt;
    @Column(name = "update_at")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate updateAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", referencedColumnName = "id")
    private Admin updatedBy;
    @OneToMany(mappedBy = "booking", fetch = FetchType.LAZY)
    private List<ReservationDishDetail> listReservationDishDetail;
    @OneToMany(mappedBy = "booking", fetch = FetchType.LAZY)
    private List<Invoice> listInvoice;
    @OneToMany(mappedBy = "booking", fetch = FetchType.LAZY)
    private List<TableReservationDetail> listTableDetail;
}
