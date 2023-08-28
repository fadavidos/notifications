package com.fabian.osorio.gila.backend.repositories;

import com.fabian.osorio.gila.backend.model.CategoryEnum;
import com.fabian.osorio.gila.backend.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findByCategoriesContaining(CategoryEnum category);
}
