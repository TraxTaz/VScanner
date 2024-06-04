package com.example.vscanner.Business.LoginInterface;

import com.example.vscanner.Domain.LoginRR.LoginRequest;
import com.example.vscanner.Domain.LoginRR.LoginResponse;

public interface LoginUseCase {
    LoginResponse login(LoginRequest request);
}
