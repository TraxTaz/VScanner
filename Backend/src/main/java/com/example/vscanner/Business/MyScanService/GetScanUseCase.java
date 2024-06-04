package com.example.vscanner.Business.MyScanService;

import com.example.vscanner.Domain.Scan;

import java.util.Optional;

public interface GetScanUseCase {
    Optional<Scan> getScan(Long scanID);
}
