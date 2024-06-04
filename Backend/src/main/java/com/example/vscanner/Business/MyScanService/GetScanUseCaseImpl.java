package com.example.vscanner.Business.MyScanService;


import com.example.vscanner.Business.Exceptions.ScanNotFoundException;
import com.example.vscanner.Domain.Scan;
import com.example.vscanner.Persistence.Entity.ScanEntity;
import com.example.vscanner.Persistence.ScanRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class GetScanUseCaseImpl implements GetScanUseCase{
    private ScanRepository scanRepository;

    @Override
    public Optional<Scan> getScan(Long scanID){
        Optional<ScanEntity> scanOpt = scanRepository.findById(scanID);

        if (scanOpt.isPresent()){
            return scanOpt.map(ScanConverter::convertToScan);
        }
        else {
            throw new ScanNotFoundException("Scan not found");
        }

    }
}
