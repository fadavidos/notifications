package com.fabian.osorio.gila.backend.services;

import com.fabian.osorio.gila.backend.model.CategoryEnum;
import com.fabian.osorio.gila.backend.model.ChannelEnum;
import com.fabian.osorio.gila.backend.model.UserEntity;
import com.fabian.osorio.gila.backend.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    void initDataBase(){
        userRepository.save(
                new UserEntity(
                        1L,
                        "One",
                        "one@gmail.com",
                        "123",
                        List.of(CategoryEnum.SPORTS),
                        List.of(ChannelEnum.SMS)
                ));
        userRepository.save(
                new UserEntity(
                        2L,
                        "Two",
                        "two@gmail.com",
                        "234",
                        List.of(CategoryEnum.SPORTS, CategoryEnum.FINANCE),
                        List.of(ChannelEnum.PUSH_NOTIFICATION)
                ));
        userRepository.save(
                new UserEntity(
                        3L,
                        "Three",
                        "three@gmail.com",
                        "345",
                        List.of(CategoryEnum.FILMS, CategoryEnum.FINANCE),
                        List.of(ChannelEnum.PUSH_NOTIFICATION, ChannelEnum.EMAIL, ChannelEnum.SMS)
                ));
    }
}
