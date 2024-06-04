package com.example.vscanner.Business.UserImpl;


import com.example.vscanner.Business.UserInterface.GetUsersUseCase;
import com.example.vscanner.Domain.User;
import com.example.vscanner.Persistence.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetUsersUseCaseImpl implements GetUsersUseCase {
    private UserRepository userRepository;

    @Transactional
    @Override
    public List<User> getUsers(){
        return userRepository.findAll()
                .stream()
                .map(UserConverter::convertToUser)
                .toList();
    }
}
