package com.fabian.osorio.gila.backend.dtos;


import java.util.Date;

public record NotificationOutDTO(Long id, String message, String category, String channel, Date creationDate, Long userId, String userName){

}
