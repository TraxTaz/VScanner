package com.example.vscanner.Business;


import com.example.vscanner.Business.Exceptions.UserExistsException;
import com.example.vscanner.Business.UserImpl.CreateUserUseCaseImpl;
import com.example.vscanner.Domain.UserRR.CreateUserRequest;
import com.example.vscanner.Domain.UserRR.CreateUserResponse;
import com.example.vscanner.Persistence.Entity.UserEntity;
import com.example.vscanner.Persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

@ExtendWith(SpringExtension.class)
public class CreateUserUseCaseImplTest {
    @Mock
    private UserRepository userRepoMock;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreateUserUseCaseImpl createUserUseCase;

    @Test
    void createUser(){
        CreateUserRequest request = new CreateUserRequest("Meda", "12345678");
        UserEntity savedUser = UserEntity
                .builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .build();

        UserEntity saveUserWithID = UserEntity.builder()
                .id(1L)
                .password(request.getPassword())
                .username(request.getUsername())
                .build();

        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(userRepoMock.save(savedUser)).thenReturn(saveUserWithID);

        CreateUserResponse response = createUserUseCase.createUser(request);
        response.setId(1L);

        assertEquals(saveUserWithID.getId(), response.getId());
    }

    @Test
    void createUserButUserExists(){
        CreateUserRequest request = new CreateUserRequest("cool", "23532523");

        when(userRepoMock.existsByUsername(request.getUsername())).thenReturn(true);

        CreateUserRequest request2 = new CreateUserRequest("cool", "434256");

        assertThrows(UserExistsException.class, () -> createUserUseCase.createUser(request2));

        verify(userRepoMock).existsByUsername("cool");
    }
}
