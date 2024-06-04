package com.example.vscanner.Business;


import com.example.vscanner.Business.Exceptions.UserNotFoundException;
import com.example.vscanner.Business.UserImpl.GetUserUseCaseImpl;
import com.example.vscanner.Domain.User;
import com.example.vscanner.Persistence.Entity.UserEntity;
import com.example.vscanner.Persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class GetUserUseCaseImplTest {
    @Mock
    private UserRepository userRepoMock;
    @InjectMocks
    private GetUserUseCaseImpl getUserUseCase;

    @Test
    void getUser(){
        UserEntity userEntity = UserEntity.builder()
                .username("Trax")
                .password("12341234121")
                .id(1L)
                .build();

        when(userRepoMock.findById(1L)).thenReturn(Optional.of(userEntity));

        Optional<User> response = getUserUseCase.getUserByID(1L);

        assertEquals(1L, response.get().getId());
    }

    @Test
    void getUser_butThrowsException(){
        UserEntity userEntity = UserEntity.builder()
                .username("Trax")
                .password("12341234121")
                .id(1L)
                .build();

        when(userRepoMock.findById(1L)).thenReturn(Optional.ofNullable(userEntity));

        assertThrows(UserNotFoundException.class, () -> getUserUseCase.getUserByID(2L));
    }
}
