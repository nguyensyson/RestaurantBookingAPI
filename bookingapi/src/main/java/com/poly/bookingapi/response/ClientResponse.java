package com.poly.bookingapi.response;

import com.poly.bookingapi.entity.Account;

import java.time.LocalDate;

public class ClientResponse {

    private Integer id;

    private String username;

    private String avatar;

    private String fullname;

    private String sdt;

    private String address;

    private String email;

    private LocalDate createAt;

    private LocalDate updateAt;
    private Account account;

}
