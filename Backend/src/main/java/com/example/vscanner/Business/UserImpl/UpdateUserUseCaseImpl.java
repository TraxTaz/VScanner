package com.example.vscanner.Business.UserImpl;

import com.example.vscanner.Business.Exceptions.UserNotFoundException;
import com.example.vscanner.Business.UserInterface.UpdateUserUseCase;
import com.example.vscanner.Domain.Role;
import com.example.vscanner.Domain.UserRR.UpdateUserRequest;
import com.example.vscanner.Persistence.Entity.UserEntity;
import com.example.vscanner.Persistence.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {
    private UserRepository userRepository;

    @Transactional
    @Override
    public void updateUser(UpdateUserRequest request){
        Optional<UserEntity> optionalUser = userRepository.findById(request.getId());

        if (optionalUser.isPresent()){
            UserEntity user = optionalUser.get();
            updateFields(request, user);
        }
        else{
            throw new UserNotFoundException("User not found");
        }
    }

    private void updateFields(UpdateUserRequest request, UserEntity userEntity){
        userEntity.setRole(Role.User);
        userEntity.setEmail(request.getEmail());
        userEntity.setFirstName(request.getFirstName());
        userEntity.setLastName(request.getLastName());
        userEntity.setUsername(request.getUsername());

        userRepository.save(userEntity);
    }
}
