package com.example.vscanner.Business.MyScanService;

import com.example.vscanner.Business.UserImpl.UserConverter;
import com.example.vscanner.Domain.Scan;
import com.example.vscanner.Persistence.Entity.ScanEntity;

public class ScanConverter {


    public static Scan convertToScan(ScanEntity scanEntity){
        return Scan.builder()
                .id(scanEntity.getId())
                .url(scanEntity.getUrl())
                .user(UserConverter.convertToUser(scanEntity.getUser()))
                .vulnerabilities(scanEntity.getVulnerabilities().stream().map(VulnerabilityConverter::convertToVulnerability).toList())
                .build();
    }

    public static ScanEntity convertToScanEntity(Scan scan){
        return ScanEntity.builder()
                .id(scan.getId())
                .url(scan.getUrl())
                .user(UserConverter.convertToUserEntity(scan.getUser()))
                .vulnerabilities(scan.getVulnerabilities().stream().map(VulnerabilityConverter::convertToVulnerabilityEntity).toList())
                .build();
    }
}
