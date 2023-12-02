package com.poly.bookingapi.repository;

import com.poly.bookingapi.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

    Optional<Token> findByStoken(String stoken);

    @Query("SELECT t FROM Token t INNER JOIN Account a "
            + "ON t.account.id = a.id "
            + "WHERE a.id = :id AND (t.expired = false OR t.revoked = false)")
    List<Token> findAllValidTokenByPhatTu(Integer id);
}
