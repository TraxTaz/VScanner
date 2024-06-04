package com.example.vscanner.Business.UserImpl;

import com.example.vscanner.Business.Exceptions.UserNotFoundException;
import com.example.vscanner.Business.UserInterface.GetUserByUseCase;
import com.example.vscanner.Domain.User;
import com.example.vscanner.Persistence.Entity.UserEntity;
import com.example.vscanner.Persistence.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetUserUseCaseImpl implements GetUserByUseCase {
    private UserRepository userRepository;

    @Override
    @Transactional
    public Optional<User> getUserByID(Long userID) {

        Optional<UserEntity> optionalUser = userRepository.findById(userID);

        if (optionalUser.isPresent()){
            return optionalUser.map(UserConverter::convertToUser);
        }
        else{
            throw new UserNotFoundException("User Not Found");
        }
    }
}
