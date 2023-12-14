package com.poly.bookingapi.entity;

import com.poly.bookingapi.dto.DinnerTableDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "dinner_table")
public class DinnerTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "table_code")
    private String tableCode;

    @Column(name = "number_of_seats")
    private Integer numberOfSeats;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dining_room_id", referencedColumnName = "id")
    private DiningRoom diningRoom;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_at")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate createdAt;

    @Column(name = "update_at")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate updateAt;

    @OneToMany(mappedBy = "dinnerTable", fetch = FetchType.LAZY)
    private List<TableDetail> listTableDetail;

    public DinnerTableDTO loadData (DinnerTableDTO dinnerTableDTO){
        dinnerTableDTO.setNumberOfSeats(numberOfSeats);
        dinnerTableDTO.setStatus(status);
        return dinnerTableDTO;
    }
}
