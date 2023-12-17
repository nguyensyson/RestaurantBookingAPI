package com.poly.bookingapi.repository;

import com.poly.bookingapi.entity.Product;
import com.poly.bookingapi.proxydto.ProductProxy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product getById(Integer id);

    //    @Query("SELECT new com.poly.bookingapi.dto.ProductViewDTO(p.id, p.category, p.avatar, p.listImage, p.nameProduct, p.price, p.discount.discountValue, p.introduce, p.status) " +
//            "FROM Product p " +
//            "WHERE p.category.id <> 1")
    @Query("SELECT p " +
        "FROM Product p " +
        "WHERE p.category.id <> 1")
    Page<Product> getAllNotCombo(Pageable pageable);

    @Query("SELECT p " +
        "FROM Product p " +
        "WHERE (:name is null or p.nameProduct LIKE %:name% ) AND p.category.id = 1")
    Page<Product> searchComboByName(String name, Pageable pageable);

    @Query("SELECT p.id AS id, " +
        "p.nameProduct AS name," +
        "p.price AS price," +
        "COALESCE(SUM(rp.quantity),0) AS quantity, " +
        "(CASE WHEN COUNT(DISTINCT rp.id) > 0 THEN TRUE ELSE FALSE END ) AS isOrdered FROM " +
        "Product p LEFT JOIN ReservationProduct rp ON (p.id = rp.product.id AND rp.reservation.id = :id) " +
        "GROUP BY p.id " +
        "ORDER BY (CASE WHEN COUNT(DISTINCT rp.id) > 0 THEN TRUE ELSE FALSE END ) DESC")
    List<ProductProxy> getAll(Integer id);
}
