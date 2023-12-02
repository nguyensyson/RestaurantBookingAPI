package com.poly.bookingapi.controller;

import com.poly.bookingapi.dto.NotificationDTO;
import com.poly.bookingapi.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(notificationService.getAll());
    }

    @PostMapping("/addNotification")
    public ResponseEntity<?>addNotification(@RequestBody NotificationDTO notificationDTO){
        return ResponseEntity.ok(notificationService.add(notificationDTO));
    }

    @PutMapping("/updateNotification/{id}")
    public ResponseEntity<?>updateNotification(@RequestBody NotificationDTO notificationDTO,@PathVariable Integer id){
        return ResponseEntity.ok(notificationService.update(notificationDTO, id));
    }

    @DeleteMapping("/deleteNotification/{id}")
    public ResponseEntity<?>deleteNotification(@PathVariable Integer id){
        return ResponseEntity.ok(notificationService.delete(id));
    }
}
