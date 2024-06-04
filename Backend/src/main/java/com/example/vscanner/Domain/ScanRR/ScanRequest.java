package com.example.vscanner.Domain.ScanRR;

import com.example.vscanner.Domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class ScanRequest {
    private String url;
    private User user;
}
