package com.fabian.osorio.gila.backend.repositories;

import com.fabian.osorio.gila.backend.model.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

    @Query("SELECT n FROM NotificationEntity n ORDER BY n.creationDate DESC")
    List<NotificationEntity> findAllOrderByCreationDateDesc();

}

