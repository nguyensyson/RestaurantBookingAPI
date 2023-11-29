package com.poly.bookingapi.service;

import com.poly.bookingapi.entity.Client;

public interface ClientService {

    Integer getAllClient();

    Client getClientByID(Integer id);
}
