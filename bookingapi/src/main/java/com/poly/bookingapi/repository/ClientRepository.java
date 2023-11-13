package com.poly.bookingapi.repository;

import com.poly.bookingapi.entity.Client;
import com.poly.bookingapi.request.ClientRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    List<Client> findByUsername(String username);

    Optional<Client> findById(Integer id);


}

