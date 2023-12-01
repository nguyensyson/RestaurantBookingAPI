package com.poly.bookingapi.entity;

import com.poly.bookingapi.dto.DiningRoomDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "dining_room")
public class DiningRoom {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "maximum_occupancy")
    private Integer maximumOccupancy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private CategoryDiningRoom category;

    @Column(name = "created_at")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate createdAt;

    @Column(name = "update_at")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate updateAt;

    @OneToMany(mappedBy = "diningRoom", fetch = FetchType.LAZY)
    private List<DinnerTable> listDinnerTable;

    public DiningRoomDTO loadData (DiningRoomDTO diningRoomDTO){
        diningRoomDTO.setMaximumOccupancy(maximumOccupancy);
        diningRoomDTO.setCreateAt(createdAt);
        diningRoomDTO.setUpdateAt(updateAt);
        return diningRoomDTO;
    }
}
