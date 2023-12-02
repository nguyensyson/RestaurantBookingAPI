package com.poly.bookingapi.entity;

import com.poly.bookingapi.dto.VoucherDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "voucher")
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id", referencedColumnName = "id")
    private Notification notification;

    @Column(name = "voucher_value")
    private Integer voucherValue;

    @Column(name = "status")
    private Integer status;

    @Column(name = "requirement")
    private Long requirement;

    @Column(name = "start_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate startDate;

    @Column(name = "end_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate endDate;

    @Column(name = "created_at")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate createdAt;

    @Column(name = "update_at")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate updateAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", referencedColumnName = "id")
    private Admin updatedBy;

    @OneToMany(mappedBy = "voucher", fetch = FetchType.LAZY)
    private List<Reservation> listInvoice;

    public VoucherDTO loadData (VoucherDTO voucher){
        voucher.setTitle(title);
        voucher.setVoucherValue(voucherValue);
        voucher.setStatus(status);
        voucher.setRequirement(requirement);
        voucher.setStartDate(startDate);
        voucher.setEndDate(endDate);
        return voucher;
    }
}
