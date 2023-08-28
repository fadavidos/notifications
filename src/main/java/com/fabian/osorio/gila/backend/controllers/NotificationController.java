package com.fabian.osorio.gila.backend.controllers;

import com.fabian.osorio.gila.backend.dtos.NotificationMessageDTO;
import com.fabian.osorio.gila.backend.dtos.NotificationOutDTO;
import com.fabian.osorio.gila.backend.services.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }


    @PostMapping("")
    public void receiveNotificationMessage(@RequestBody NotificationMessageDTO notificationMessageDTO) {
        notificationService.processNotification(notificationMessageDTO);
    }

    @GetMapping("")
    public List<NotificationOutDTO> getNotificationsOrderedByDate() {
        return notificationService.getAllNotificationsOrderedByCreationDateDesc();
    }

}
