package com.example.vscanner.Business.UserInterface;

import com.example.vscanner.Domain.User;

import java.util.Optional;

public interface GetUserByUseCase {
    Optional<User> getUserByID(Long userID);
}
