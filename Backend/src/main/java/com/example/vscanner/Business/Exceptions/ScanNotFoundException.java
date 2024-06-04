package com.example.vscanner.Business.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ScanNotFoundException extends ResponseStatusException {
    public ScanNotFoundException (String error) {super (HttpStatus.BAD_REQUEST, error);}
}
