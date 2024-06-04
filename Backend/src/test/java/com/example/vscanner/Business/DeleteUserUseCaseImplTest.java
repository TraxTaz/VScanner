package com.example.vscanner.Business;


import com.example.vscanner.Business.Exceptions.UserNotFoundException;
import com.example.vscanner.Business.UserImpl.DeleteUserUseCaseImpl;
import com.example.vscanner.Business.UserImpl.GetUsersUseCaseImpl;
import com.example.vscanner.Persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class DeleteUserUseCaseImplTest {
    @Mock
    private UserRepository userRepoMock;
    @InjectMocks
    private DeleteUserUseCaseImpl deleteUserUseCase;

    @Test
    void deleteUserByID() {
        Long userId = 1L;

        when(userRepoMock.existsById(userId)).thenReturn(true);

        deleteUserUseCase.deleteUserByID(userId);

        verify(userRepoMock).deleteById(userId);
    }

    @Test
    void deleteUserByID_ButThrowsAnException(){
        Long userId = 2L;

        when(userRepoMock.existsById(userId)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> deleteUserUseCase.deleteUserByID(userId));
    }
}
