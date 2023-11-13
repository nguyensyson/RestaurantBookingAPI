package com.poly.bookingapi.request;

import com.poly.bookingapi.entity.Account;
import com.poly.bookingapi.entity.Client;
import com.poly.bookingapi.entity.Reservation;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequest {

    private String username;

    private String avatar;

    private String fullname;

    private String sdt;

    private String address;

    private String email;

    private LocalDate createAt;

    private LocalDate updateAt;
    private Account account;
    private List<Reservation>  reservations;


}
