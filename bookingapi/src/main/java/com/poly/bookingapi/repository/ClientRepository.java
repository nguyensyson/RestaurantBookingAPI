package com.poly.bookingapi.repository;

import com.poly.bookingapi.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    @Query(value = "select count(*) from client ",nativeQuery = true)
    Integer getAllClient();

    Client getClientById(Integer id);

    @Query("SELECT c FROM Client c WHERE c.account.id = :id")
    Client getByAccount(@Param("id") Integer id);

    @Query("SELECT c FROM Client c WHERE c.sdt LIKE :sdt")
    Client getBySDT(@Param("sdt") String sdt);
}
