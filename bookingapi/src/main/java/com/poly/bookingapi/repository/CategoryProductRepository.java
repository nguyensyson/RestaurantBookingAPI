package com.poly.bookingapi.repository;

import com.poly.bookingapi.entity.CategoryProduct;
import com.poly.bookingapi.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryProductRepository extends JpaRepository<CategoryProduct, Integer> {

    @Query("SELECT c FROM CategoryProduct c")
    Page<CategoryProduct> getAll(Pageable pageable);
}
