package com.poly.bookingapi.service.impl;

import com.poly.bookingapi.dto.NotificationDTO;
import com.poly.bookingapi.entity.Notification;
import com.poly.bookingapi.repository.NotificationRepository;
import com.poly.bookingapi.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public List<NotificationDTO> getAll() {
        List<Notification> getList = notificationRepository.findAll();
        //Chuyển các entity thành các đối tượng Data Transfer Object(DTO) rồi trả về cho controller
        List<NotificationDTO> getListDto = new ArrayList<>();
        for (Notification i : getList) {
            NotificationDTO notificationDTO = new NotificationDTO();
            NotificationDTO dtos = i.loadData(notificationDTO);
            getListDto.add(dtos);
        }
        return getListDto;
    }

    @Override
    public Notification add(NotificationDTO notificationDTO) {
        Notification notification = new Notification();
        notification.setContent(notificationDTO.getContent());
        notification.setTitle(notificationDTO.getTitle());
        notification.setCreatedAt(LocalDate.now());
        return notificationRepository.save(notification);
    }

    @Override
    public Notification update(NotificationDTO notificationDTO, Integer id) {
        Optional<Notification> optional = notificationRepository.findById(id);
        return optional.map(notification -> {
            notification.setContent(notificationDTO.getContent());
            notification.setTitle(notificationDTO.getTitle());
            return notificationRepository.save(notification);
        }).orElse(null);
    }

    @Override
    public Notification delete(Integer id) {
        Optional<Notification> optional = notificationRepository.findById(id);
        return optional.map(notification -> {
            notificationRepository.delete(notification);
            return notification;
        }).orElse(null);
    }
}
