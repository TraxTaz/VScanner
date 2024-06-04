package com.example.vscanner.Business.UserInterface;

import com.example.vscanner.Domain.UserRR.CreateUserRequest;
import com.example.vscanner.Domain.UserRR.CreateUserResponse;

public interface CreateUserUseCase {
    CreateUserResponse createUser(CreateUserRequest request);
}
