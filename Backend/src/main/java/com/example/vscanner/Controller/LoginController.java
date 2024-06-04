package com.example.vscanner.Controller;

import com.example.vscanner.Business.LoginInterface.LoginUseCase;
import com.example.vscanner.Domain.LoginRR.LoginRequest;
import com.example.vscanner.Domain.LoginRR.LoginResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tokens")
@CrossOrigin(origins = "http://localhost:5173")
@AllArgsConstructor
public class LoginController {
    private LoginUseCase loginUseCase;

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request){
        LoginResponse response = loginUseCase.login(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
