package com.example.vscanner.Business;


import com.example.vscanner.Business.UserImpl.GetUsersUseCaseImpl;
import com.example.vscanner.Domain.User;
import com.example.vscanner.Persistence.Entity.UserEntity;
import com.example.vscanner.Persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class GetUsersUseCaseImplTest {
    @Mock
    private UserRepository userRepoMock;
    @InjectMocks
    private GetUsersUseCaseImpl getUsersUseCase;

    @Test
    void getUsers(){

        UserEntity newUserWithIDEntity = UserEntity.builder()
                .id(1L)
                .username("Meda")
                .password("123456778")
                .build();

        UserEntity newUser2WithIDEntity = UserEntity.builder()
                .id(2L)
                .username("Trax")
                .password("123123123123")
                .build();

        when(userRepoMock.findAll()).thenReturn(List.of(newUserWithIDEntity, newUser2WithIDEntity));

        List<User> responseResult = getUsersUseCase.getUsers();

        User newUser = User.builder()
                .id(1L)
                .username("Meda")
                .password("123456778")
                .build();

        User newUser2 = User.builder()
                .id(2L)
                .username("Trax")
                .password("123123123123")
                .build();

        List<User> expected = List.of(newUser, newUser2);

        assertEquals(expected, responseResult);
    }
}
