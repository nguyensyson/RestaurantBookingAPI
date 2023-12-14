package com.poly.bookingapi.controller;

import com.poly.bookingapi.dto.NotificationDTO;
import com.poly.bookingapi.entity.Notification;
import com.poly.bookingapi.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("api/view/notification/getAll")
    public ResponseEntity<List<NotificationDTO>> getAll() {
        return ResponseEntity.ok(notificationService.getAll());
    }

    @PostMapping("api/admin/notification/add")
    public ResponseEntity<Notification> addNotification(@RequestBody NotificationDTO notificationDTO) {
        return ResponseEntity.ok(notificationService.add(notificationDTO));
    }

    @PutMapping("api/admin/notification/update/{id}")
    public ResponseEntity<Notification> updateNotification(@RequestBody NotificationDTO notificationDTO, @PathVariable Integer id) {
        return ResponseEntity.ok(notificationService.update(notificationDTO, id));
    }

    @DeleteMapping("api/admin/notification/delete/{id}")
    public ResponseEntity<Notification> deleteNotification(@PathVariable Integer id) {
        return ResponseEntity.ok(notificationService.delete(id));
    }
}
