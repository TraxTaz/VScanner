package com.example.vscanner.Business;


import com.example.vscanner.Business.UserImpl.UpdateUserUseCaseImpl;
import com.example.vscanner.Domain.UserRR.UpdateUserRequest;
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
public class UpdateUserInfoUseCaseImplTest {

    @Mock
    private UserRepository userRepoMock;

    @InjectMocks
    private UpdateUserUseCaseImpl updateUserUseCase;

    @Test
    void updateUserInfo(){
        UpdateUserRequest request = new UpdateUserRequest(1L, "Trax");

        UserEntity user = UserEntity.builder()
                .id(1L)
                .username("Meda")
                .password("1234561234")
                .build();

        UserEntity updatedUser = UserEntity.builder()
                .id(1L)
                .username(request.getUsername())
                .password("1234561234")
                .build();

        when(userRepoMock.findByUsername(user.getUsername())).thenReturn(user);
        when(userRepoMock.findById(1L)).thenReturn(Optional.of(user));
        when(userRepoMock.save(updatedUser)).thenReturn(updatedUser);

        updateUserUseCase.updateUser(request);

        assertEquals(updatedUser.getUsername(), request.getUsername());
        assertEquals(updatedUser.getId(), request.getId());
    }

}
