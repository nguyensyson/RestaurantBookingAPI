package com.poly.bookingapi.service.impl;

import com.poly.bookingapi.entity.Client;
import com.poly.bookingapi.repository.ClientRepository;
import com.poly.bookingapi.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Integer getAllClient() {
        return null;
    }

    @Override
    public Client getClientByID(Integer id) {
        return clientRepository.getClientById(id);
    }
}
