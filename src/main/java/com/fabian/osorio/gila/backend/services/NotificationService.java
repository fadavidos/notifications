package com.fabian.osorio.gila.backend.services;

import com.fabian.osorio.gila.backend.dtos.NotificationMessageDTO;
import com.fabian.osorio.gila.backend.dtos.NotificationOutDTO;
import com.fabian.osorio.gila.backend.model.CategoryEnum;
import com.fabian.osorio.gila.backend.model.ChannelEnum;
import com.fabian.osorio.gila.backend.model.NotificationEntity;
import com.fabian.osorio.gila.backend.repositories.NotificationRepository;
import com.fabian.osorio.gila.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final UserRepository userRepository;

    private final NotificationRepository notificationRepository;

    private final Map<ChannelEnum, ChannelStrategy> channelStrategyMap = new HashMap<>();

    @Autowired
    public NotificationService(UserRepository userRepository, List<ChannelStrategy> channelStrategyMap, NotificationRepository notificationRepository) {
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
        initializeStrategies(channelStrategyMap);
    }

    private void initializeStrategies(List<ChannelStrategy> listChannels) {
        for (ChannelStrategy strategy : listChannels) {
            channelStrategyMap.put(strategy.getSupportedChannel(), strategy);
        }
    }

    public Map<Long, List<String>> processNotification(NotificationMessageDTO notificationMessageDTO) {
        return userRepository.findByCategoriesContaining(notificationMessageDTO.category())
                .parallelStream()
                .flatMap(user ->
                        user.getChannels()
                                .parallelStream()
                                .map(channelEnum ->
                                        channelStrategyMap.get(channelEnum)
                                                .notifyUser(user, notificationMessageDTO.message(), notificationMessageDTO.category())
                                )
                                .toList()
                                .stream()
                                .map(notification -> {
                                    notificationRepository.save(notification);
                                    return Map.entry(user.getId(), notification.getChannel().name());
                                })
                )
                .collect(Collectors.groupingByConcurrent(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ));

    }

    public List<NotificationOutDTO> getAllNotificationsOrderedByCreationDateDesc() {
        List<NotificationEntity> notifications = notificationRepository.findAllOrderByCreationDateDesc();
        List<NotificationOutDTO> copiedNotifications = new ArrayList<>();
        for (NotificationEntity notification : notifications) {
            copiedNotifications.add(new NotificationOutDTO(
                    notification.getId(),
                    notification.getMessage(),
                    notification.getCategory().name(),
                    notification.getChannel().name(),
                    notification.getCreationDate(),
                    notification.getUser().getId(),
                    notification.getUser().getName()
            ));
        }

        return copiedNotifications;
    }

    public List<String> getAllCategories() {
        return Arrays.stream(CategoryEnum.values()).map(Enum::name).toList();
    }
}
