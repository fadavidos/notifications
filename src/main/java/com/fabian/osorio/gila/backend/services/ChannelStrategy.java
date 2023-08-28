package com.fabian.osorio.gila.backend.services;

import com.fabian.osorio.gila.backend.model.CategoryEnum;
import com.fabian.osorio.gila.backend.model.ChannelEnum;
import com.fabian.osorio.gila.backend.model.NotificationEntity;
import com.fabian.osorio.gila.backend.model.UserEntity;

public interface ChannelStrategy {

    ChannelEnum getSupportedChannel();

    NotificationEntity notifyUser(UserEntity user, String message, CategoryEnum category);
}

