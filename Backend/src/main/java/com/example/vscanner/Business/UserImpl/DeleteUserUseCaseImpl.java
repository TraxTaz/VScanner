package com.example.vscanner.Business.UserImpl;

import com.example.vscanner.Business.Exceptions.UserNotFoundException;
import com.example.vscanner.Business.UserInterface.DeleteUserUseCase;
import com.example.vscanner.Persistence.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {
    private UserRepository userRepository;

    @Transactional
    @Override
    public void deleteUserByID(Long id){
        if (userRepository.existsById(id)){
            userRepository.deleteById(id);
        }
        else{
            throw new UserNotFoundException("User not found");
        }
    }
}
