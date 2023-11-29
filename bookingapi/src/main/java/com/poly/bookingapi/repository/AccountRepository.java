package com.poly.bookingapi.repository;

import com.poly.bookingapi.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query("SELECT p FROM Account p WHERE p.username LIKE :username")
    Optional<Account> findByUsername(@Param("username") String username);

    Optional<Account> findByResetPasswordToken(String token);
}
