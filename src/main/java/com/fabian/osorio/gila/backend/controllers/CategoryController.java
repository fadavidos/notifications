package com.fabian.osorio.gila.backend.controllers;

import com.fabian.osorio.gila.backend.dtos.NotificationMessageDTO;
import com.fabian.osorio.gila.backend.dtos.NotificationOutDTO;
import com.fabian.osorio.gila.backend.services.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final NotificationService notificationService;

    public CategoryController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }


    @GetMapping("")
    public List<String> getNotificationsOrderedByDate() {
        return notificationService.getAllCategories();
    }

}
