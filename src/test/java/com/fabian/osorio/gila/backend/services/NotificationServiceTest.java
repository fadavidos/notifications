package com.fabian.osorio.gila.backend.services;

import com.fabian.osorio.gila.backend.dtos.NotificationMessageDTO;
import com.fabian.osorio.gila.backend.dtos.NotificationOutDTO;
import com.fabian.osorio.gila.backend.model.CategoryEnum;
import com.fabian.osorio.gila.backend.model.ChannelEnum;
import com.fabian.osorio.gila.backend.model.NotificationEntity;
import com.fabian.osorio.gila.backend.model.UserEntity;
import com.fabian.osorio.gila.backend.repositories.NotificationRepository;
import com.fabian.osorio.gila.backend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Map;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    NotificationService notificationService;

    @Mock
    NotificationRepository notificationRepository;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        ArrayList<ChannelStrategy> channels = new ArrayList<>();
        channels.add(new SMSChannel());
        channels.add(new EmailChannel());
        channels.add(new PushNotificationChannel());
        this.notificationService = new NotificationService(userRepository, channels, notificationRepository);
    }

    @Test
    void sendNotificationToAUserWithTwoChannels(){
        UserEntity user1 = new UserEntity(
                1L,
                "one",
                "one@one",
                "389", List.of(CategoryEnum.SPORTS),
                List.of(ChannelEnum.SMS, ChannelEnum.PUSH_NOTIFICATION)
        );

        when(userRepository.findByCategoriesContaining(CategoryEnum.SPORTS)).thenReturn(List.of(user1));
        NotificationMessageDTO messageDTO = new NotificationMessageDTO(CategoryEnum.SPORTS, "hello");
        Map<Long, List<String>> resultMap = notificationService.processNotification(messageDTO);
        System.out.println(resultMap);
        assertThat(resultMap).containsKeys(1L);
        assertThat(resultMap.get(1L)).contains("SMS", "PUSH_NOTIFICATION");
    }

    @Test
    void sendNotificationTwoUserWithTwoChannels(){
        UserEntity user1 = new UserEntity(
                1L,
                "one",
                "one@one",
                "389", List.of(CategoryEnum.SPORTS),
                List.of(ChannelEnum.SMS, ChannelEnum.PUSH_NOTIFICATION)
        );
        UserEntity user2 = new UserEntity(
                2L,
                "two",
                "two@two",
                "987", List.of(CategoryEnum.SPORTS),
                List.of(ChannelEnum.PUSH_NOTIFICATION, ChannelEnum.EMAIL)
        );

        when(userRepository.findByCategoriesContaining(CategoryEnum.SPORTS)).thenReturn(List.of(user1, user2));
        NotificationMessageDTO messageDTO = new NotificationMessageDTO(CategoryEnum.SPORTS, "hello");
        Map<Long, List<String>> resultMap = notificationService.processNotification(messageDTO);
        System.out.println(resultMap);
        assertThat(resultMap).containsKeys(1L, 2L);
        assertThat(resultMap.get(1L)).contains("SMS", "PUSH_NOTIFICATION");
        assertThat(resultMap.get(2L)).contains("PUSH_NOTIFICATION", "EMAIL");
    }

    @Test
    void getNotifications(){

        UserEntity user1 = new UserEntity(
                1L,
                "one",
                "onw@onw",
                "876", List.of(CategoryEnum.SPORTS),
                List.of(ChannelEnum.SMS, ChannelEnum.EMAIL)
        );

        NotificationEntity notification1 = new NotificationEntity(
                "notification1",
                CategoryEnum.SPORTS,
                ChannelEnum.SMS,
                user1
        );

        NotificationEntity notification2 = new NotificationEntity(
                "notification1",
                CategoryEnum.SPORTS,
                ChannelEnum.EMAIL,
                user1
        );

        UserEntity user2 = new UserEntity(
                2L,
                "two",
                "two@two",
                "987", List.of(CategoryEnum.SPORTS),
                List.of(ChannelEnum.PUSH_NOTIFICATION, ChannelEnum.EMAIL)
        );

        NotificationEntity notification3 = new NotificationEntity(
                "notification1",
                CategoryEnum.SPORTS,
                ChannelEnum.PUSH_NOTIFICATION,
                user2
        );

        NotificationEntity notification4 = new NotificationEntity(
                "notification1",
                CategoryEnum.SPORTS,
                ChannelEnum.EMAIL,
                user2
        );

        when(notificationRepository.findAllOrderByCreationDateDesc())
                .thenReturn(List.of(notification1, notification2, notification3, notification4));
        List<NotificationOutDTO> result = notificationService.getAllNotificationsOrderedByCreationDateDesc();
        System.out.println(result);
        assertThat(result.size()).isEqualTo(4);
        assertThat(result.get(0).userId()).isEqualTo(user1.getId());
        assertThat(result.get(1).userId()).isEqualTo(user1.getId());
        assertThat(result.get(2).userId()).isEqualTo(user2.getId());
        assertThat(result.get(3).userId()).isEqualTo(user2.getId());
    }


}
