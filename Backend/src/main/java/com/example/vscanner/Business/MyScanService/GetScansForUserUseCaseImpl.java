package com.example.vscanner.Business.MyScanService;


import com.example.vscanner.Business.Exceptions.UserNotFoundException;
import com.example.vscanner.Domain.Scan;
import com.example.vscanner.Persistence.Entity.ScanEntity;
import com.example.vscanner.Persistence.Entity.UserEntity;
import com.example.vscanner.Persistence.ScanRepository;
import com.example.vscanner.Persistence.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class GetScansForUserUseCaseImpl implements GetScansForUserUseCase{
    private ScanRepository scanRepository;
    private UserRepository userRepository;

    @Override
    public List<Scan> getScansForUser(Long userId){
        Optional<UserEntity> userEntity = userRepository.findById(userId);

        if (userEntity.isPresent()){
            return scanRepository.findAllByUser(userEntity.get())
                    .stream()
                    .map(ScanConverter::convertToScan)
                    .toList();
        }
        else{
            throw new UserNotFoundException("User not found");
        }
    }
}
