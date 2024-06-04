package com.example.vscanner.Controller;

import com.example.vscanner.Business.Exceptions.ScanNotFoundException;
import com.example.vscanner.Business.MyScanService.GetScanUseCase;
import com.example.vscanner.Business.MyScanService.GetScansForUserUseCase;
import com.example.vscanner.Business.MyScanService.MyScanService;
import com.example.vscanner.Business.ScanService;
import com.example.vscanner.Domain.Scan;
import com.example.vscanner.Domain.ScanRR.ScanResponse;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/scan")
@CrossOrigin(origins = "http://localhost:5173")
@AllArgsConstructor
public class ScanController {
    private final ScanService scanService;
    private MyScanService myScanService;
    private GetScanUseCase getScanUseCase;
    private GetScansForUserUseCase getScansForUserUseCase;

    @PostMapping
    public ResponseEntity<Resource> scanWebsite(@RequestBody String url) throws UnsupportedEncodingException {
        String decodedUrl = URLDecoder.decode(url, "UTF-8");
        scanService.scanWebsite(decodedUrl);
        String reportPath = scanService.afterScan();

        if (reportPath != null) {
            Resource file = new FileSystemResource(reportPath);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Security-Zap-Report.html")
                    .body(file);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("{userID}")
    public ResponseEntity<ScanResponse> scanWebsite(@RequestBody String url, @PathVariable(value = "userID") Long userID) throws UnsupportedEncodingException {
        String decodedUrl = URLDecoder.decode(url, "UTF-8");
        ScanResponse response = myScanService.scanForSqlInjection(decodedUrl, userID);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("{scanID}")
    public ResponseEntity<Scan> getScan(@PathVariable(value = "scanID") Long scanID){
        Optional<Scan> scanOpt = getScanUseCase.getScan(scanID);

        if (scanOpt.isEmpty()){
            throw new ScanNotFoundException("Scan not found");
        }
        return ResponseEntity.ok().body(scanOpt.get());
    }

    @GetMapping("/getScans/{userId}")
    public ResponseEntity<List<Scan>> getScansForUser(@PathVariable(value = "userId") Long userID){
        return ResponseEntity.ok(getScansForUserUseCase.getScansForUser(userID));
    }
}
