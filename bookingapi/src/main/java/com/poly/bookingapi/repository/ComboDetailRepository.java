package com.poly.bookingapi.repository;

import com.poly.bookingapi.entity.ComboDetail;
import com.poly.bookingapi.entity.ReservationProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ComboDetailRepository extends JpaRepository<ComboDetail, Integer> {

    @Query("SELECT t FROM ComboDetail t "
            + "WHERE t.combo.id = :idCombo AND t.item.id = :idProduct")
    ComboDetail findByComboAndProduct(@Param("idCombo") Integer idCombo,
                                             @Param("idProduct") Integer idProduct);

}
