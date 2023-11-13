package com.poly.bookingapi.service;

import com.poly.bookingapi.entity.Client;
import com.poly.bookingapi.entity.Reservation;
import com.poly.bookingapi.request.ClientRequest;

import java.util.List;
import java.util.Optional;

public interface ManageClientService {
    List<ClientRequest> findAll(Integer page);
//    Page<Client> findAll(Pageable pageable);
    List<Client> searchByName(String username);
    Optional<Client> getClientById(Integer id);
    List<Reservation> getReservationsByClient(Optional<Client> client);
}
