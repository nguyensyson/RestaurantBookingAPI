package com.poly.bookingapi.service;

import com.poly.bookingapi.dto.NotificationDTO;
import com.poly.bookingapi.entity.Notification;

import java.util.List;

public interface NotificationService {
    List<NotificationDTO>getAll();
    Notification add(NotificationDTO notificationDTO);
    Notification update(NotificationDTO notificationDTO,Integer id);
    Notification delete(Integer id);
}
