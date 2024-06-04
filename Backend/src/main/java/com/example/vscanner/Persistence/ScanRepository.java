package com.example.vscanner.Persistence;

import com.example.vscanner.Persistence.Entity.ScanEntity;
import com.example.vscanner.Persistence.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScanRepository extends JpaRepository<ScanEntity, Long> {
    List<ScanEntity> findAllByUser(UserEntity userEntity);
}
