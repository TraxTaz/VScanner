package com.example.vscanner.Domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Scan {
    private Long id;
    private String url;
    private User user;
    private List<Vulnerability> vulnerabilities;
}
