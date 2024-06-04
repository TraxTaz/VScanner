package com.example.vscanner.Business.LoginImpl;

import com.example.vscanner.Business.Exceptions.LoginInvalidException;
import com.example.vscanner.Business.LoginInterface.LoginUseCase;
import com.example.vscanner.Config.Token.AccessTokenEncoder;
import com.example.vscanner.Config.Token.Impl.AccessTokenImpl;
import com.example.vscanner.Domain.LoginRR.LoginRequest;
import com.example.vscanner.Domain.LoginRR.LoginResponse;
import com.example.vscanner.Persistence.Entity.UserEntity;
import com.example.vscanner.Persistence.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Transactional
@Service
@AllArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenEncoder accessTokenEncoder;

    @Override
    public LoginResponse login(LoginRequest request){
        UserEntity userEntity = userRepository.findByUsername(request.getUsername());

        if (userEntity == null){
            throw new LoginInvalidException("User not found");
        }

        if (!matchesPassword(request.getPassword(), userEntity.getPassword())){
            throw new LoginInvalidException("Either password or username is incorrect");
        }

        String accessToken = generateAccessToken(userEntity);
        return LoginResponse.builder().token(accessToken).build();
    }

    private boolean matchesPassword(String rawPassword, String encodedPassword){
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private String generateAccessToken(UserEntity userEntity){
        Long userId = userEntity.getId();
        String role = userEntity.getRole().toString();

        return accessTokenEncoder.encode(new AccessTokenImpl(userEntity.getUsername(), userId, role));
    }
}
