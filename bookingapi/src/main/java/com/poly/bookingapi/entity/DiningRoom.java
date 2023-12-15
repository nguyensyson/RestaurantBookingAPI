package com.poly.bookingapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poly.bookingapi.dto.DiningRoomDTO;
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
@Table(name = "dining_room")
public class DiningRoom {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    private Integer maximumOccupancy;
    private String name;
    private Integer numberOfAvailable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private CategoryDiningRoom category;

    private LocalDate createdAt;
    private LocalDate updateAt;

    @OneToMany(mappedBy = "diningRoom", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<DinnerTable> listDinnerTable;

    @OneToMany(mappedBy = "diningRoom", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<TableDetail> listTableDetail;

    public DiningRoomDTO loadData (DiningRoomDTO diningRoomDTO){
        diningRoomDTO.setMaximumOccupancy(maximumOccupancy);
        diningRoomDTO.setNumberOfAvailable(numberOfAvailable);
        return diningRoomDTO;
    }
}
