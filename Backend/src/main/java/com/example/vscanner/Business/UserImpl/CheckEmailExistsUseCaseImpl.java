package com.example.vscanner.Business.UserImpl;

import com.example.vscanner.Business.UserInterface.CheckEmailExistsUseCase;
import com.example.vscanner.Persistence.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Transactional
public class CheckEmailExistsUseCaseImpl implements CheckEmailExistsUseCase {
    private UserRepository userRepository;

    @Override
    public boolean checkEmailExists(String email){
        return userRepository.existsByEmail(email);
    }
}
