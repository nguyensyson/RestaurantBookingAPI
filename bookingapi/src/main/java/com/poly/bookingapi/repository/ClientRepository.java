package com.poly.bookingapi.repository;

import com.poly.bookingapi.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    @Query(value = "select count(*) from client ",nativeQuery = true)
    Integer getAllClient();

    Client getClientById(Integer id);
}
