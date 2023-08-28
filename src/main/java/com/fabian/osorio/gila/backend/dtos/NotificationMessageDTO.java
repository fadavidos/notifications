package com.fabian.osorio.gila.backend.dtos;

import com.fabian.osorio.gila.backend.model.CategoryEnum;

public record NotificationMessageDTO(CategoryEnum category, String message){}