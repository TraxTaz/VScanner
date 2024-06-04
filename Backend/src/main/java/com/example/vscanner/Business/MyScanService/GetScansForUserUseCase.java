package com.example.vscanner.Business.MyScanService;

import com.example.vscanner.Domain.Scan;

import java.util.List;

public interface GetScansForUserUseCase {
    List<Scan> getScansForUser(Long userId);
}
