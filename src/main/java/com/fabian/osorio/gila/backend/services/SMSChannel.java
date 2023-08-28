package com.fabian.osorio.gila.backend.services;

import com.fabian.osorio.gila.backend.model.CategoryEnum;
import com.fabian.osorio.gila.backend.model.ChannelEnum;
import com.fabian.osorio.gila.backend.model.NotificationEntity;
import com.fabian.osorio.gila.backend.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class SMSChannel implements ChannelStrategy {

    @Override
    public ChannelEnum getSupportedChannel() {
        return ChannelEnum.SMS;
    }

    private NotificationEntity build(UserEntity userEntity, CategoryEnum category, String message) {
        return new NotificationEntity(
                message,
                category,
                ChannelEnum.SMS,
                userEntity
        );
    }

    @Override
    public NotificationEntity notifyUser(UserEntity user, String message, CategoryEnum category) {
        System.out.printf(
                "We will send this message: ´%s´ of %s category via %s to the user %s%n",
                message,
                category.name(),
                ChannelEnum.SMS.name(),
                user.getId().toString()
        );
        return build(user, category, message);
    }

}
