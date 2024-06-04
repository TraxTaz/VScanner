package com.example.vscanner.Persistence;

import com.example.vscanner.Persistence.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsername(String userName);

    UserEntity findByUsername(String userName);

    boolean existsByEmail(String email);
}
