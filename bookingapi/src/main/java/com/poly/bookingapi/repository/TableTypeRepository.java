package com.poly.bookingapi.repository;

import com.poly.bookingapi.entity.TableType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableTypeRepository extends JpaRepository<TableType, Integer> {
}
