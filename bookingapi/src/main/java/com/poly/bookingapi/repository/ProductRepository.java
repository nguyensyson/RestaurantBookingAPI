package com.poly.bookingapi.repository;

import com.poly.bookingapi.dto.ProductViewDTO;
import com.poly.bookingapi.entity.Product;
import com.poly.bookingapi.proxydto.ProductProxy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("select p.id as id, p.nameProduct as name, p.price as price," +
        " (case when  (select count(rp) from ReservationProduct rp where rp.reservation.id = :id and rp.product.id = p.id) > 0 then true else false end) as isOrdered " +
        "from Product p " +
        "order by p.id desc")
    List<ProductProxy> getAll(Integer id);
}
