package org.example.Lab2_BLPS.service.authorization.service.repository;

import org.example.Lab2_BLPS.service.authorization.model.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    boolean existsByUsername(String username);

    UserEntity findByUsername(String username);
}
