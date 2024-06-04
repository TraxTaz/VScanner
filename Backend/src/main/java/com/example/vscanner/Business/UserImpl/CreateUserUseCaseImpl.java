package com.example.vscanner.Business.UserImpl;

import com.example.vscanner.Business.Exceptions.UserExistsException;
import com.example.vscanner.Business.UserInterface.CreateUserUseCase;
import com.example.vscanner.Domain.Role;
import com.example.vscanner.Domain.UserRR.CreateUserRequest;
import com.example.vscanner.Domain.UserRR.CreateUserResponse;
import com.example.vscanner.Persistence.Entity.UserEntity;
import com.example.vscanner.Persistence.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CreateUserResponse createUser(CreateUserRequest request){
        if (userRepository.existsByUsername(request.getUsername())){
            throw new UserExistsException("Username exists");
        }
        else{
            UserEntity userEntity = fillUserFields(request);
            userRepository.save(userEntity);
            return CreateUserResponse.builder().id(userEntity.getId()).build();
        }
    }

    private UserEntity fillUserFields(CreateUserRequest request){

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        return UserEntity.builder()
                .username(request.getUsername())
                .password(encodedPassword)
                .email(request.getEmail())
                .FirstName(request.getFirstName())
                .LastName(request.getLastName())
                .role(Role.User)
                .build();
    }
}
