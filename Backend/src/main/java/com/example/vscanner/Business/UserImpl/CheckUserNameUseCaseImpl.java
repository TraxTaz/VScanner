package com.example.vscanner.Business.UserImpl;

import com.example.vscanner.Business.Exceptions.UserNotFoundException;
import com.example.vscanner.Business.UserInterface.CheckUserNameUseCase;
import com.example.vscanner.Persistence.Entity.UserEntity;
import com.example.vscanner.Persistence.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Transactional
@AllArgsConstructor
public class CheckUserNameUseCaseImpl implements CheckUserNameUseCase {
    private UserRepository userRepository;

    @Override
    public boolean checkUsernameExists(String username) {
        return userRepository.existsByUsername(username);
    }
}
