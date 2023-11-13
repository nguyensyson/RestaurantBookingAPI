package com.poly.bookingapi.service.impl;

import com.poly.bookingapi.entity.Client;
import com.poly.bookingapi.entity.Reservation;
import com.poly.bookingapi.repository.ClientRepository;
import com.poly.bookingapi.repository.ReservationRepository;
import com.poly.bookingapi.request.ClientRequest;
import com.poly.bookingapi.service.ManageClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ManageClientServiceImpl implements ManageClientService {


    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public List<ClientRequest> findAll(Integer page) {
        List<ClientRequest> clientRequest = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, 2);
        Page<Client> client = clientRepository.findAll(pageable);

        for (Client i : client.getContent()) {
            ClientRequest dto = i.loadDataClientRequest();
            clientRequest.add(dto);
        }
        return clientRequest;
    }

    @Override
    public List<Client> searchByName(String username) {
        return clientRepository.findByUsername(username);
    }

    @Override
    public Optional<Client> getClientById(Integer id) {
        return clientRepository.findById(id);


    }

    @Override
    public List<Reservation> getReservationsByClient(Optional<Client> client) {
        return reservationRepository.findByClientOrderByReservationDateDesc(client);
    }
}
