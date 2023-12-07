package com.poly.bookingapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "table_detail")
public class TableDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reservation", referencedColumnName = "id")
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_room", referencedColumnName = "id")
    private DiningRoom diningRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_table", referencedColumnName = "id")
    private DinnerTable dinnerTable;

    @Column(name = "created_at")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate createdAt;

    @Column(name = "update_at")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate updateAt;
}
