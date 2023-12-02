package com.poly.bookingapi.entity;

import com.poly.bookingapi.dto.NotificationDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate createdAt;

    @Column(name = "update_at")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate updateAt;

    @OneToMany(mappedBy = "notification", fetch = FetchType.LAZY)
    private List<ImagePage> listImage;

    @OneToMany(mappedBy = "notification", fetch = FetchType.LAZY)
    private List<Voucher> listVoucher;

    public NotificationDTO loadData (NotificationDTO notificationDTO){
        notificationDTO.setContent(content);
        notificationDTO.setTitle(title);
        notificationDTO.setCreatedAt(createdAt);
        return notificationDTO;
    }
}
