package com.example.vscanner.Business.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LoginInvalidException extends ResponseStatusException {
    public LoginInvalidException (String errorMessage) { super (HttpStatus.BAD_REQUEST, errorMessage); }
}
