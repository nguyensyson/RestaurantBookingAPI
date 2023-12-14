package com.poly.bookingapi.proxydto;

import com.poly.bookingapi.entity.DinnerTable;

/**
 * DinnerTableProxy is a proxy class for DinnerTable and DiningRoom
 * This class is used to get DinnerTable and DiningRoom in one request
 * This class is used in DinnerTableController
 */
public interface DinnerTableProxy {
    Integer getId();
    String getTableCode();
    Integer getNumberOfSeats();
    Integer getStatus();
}
