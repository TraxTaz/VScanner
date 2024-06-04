package com.example.vscanner.Business;

import org.springframework.stereotype.Service;
import org.zaproxy.clientapi.core.ApiResponse;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;

import java.nio.file.Paths;


@Service
public class ScanService {
    static final int ZAP_PORT = 8081;
    static final String ZAP_ADDRESS = "localhost";
    static final String ZAP_API_Key = "pdk9pf3o0kpmicllfu55gfqbba";
    private ClientApi api;

    public ScanService () {
        api = new ClientApi(ZAP_ADDRESS, ZAP_PORT, ZAP_API_Key);
    }

    public void scanWebsite(String url){
        try {
            ApiResponse spiderResponse = api.spider.scan(url, null, null, null, null);
            String spiderScanID = spiderResponse.toString();

            while (true) {
                Thread.sleep(1000); // Wait for 1 second before polling again
                ApiResponse statusResponse = api.spider.status(spiderScanID);
                String status = statusResponse.toString();
                if (status.equals("100")) {
                    System.out.println("Spider scan completed.");
                    break;
                }
            }

            // Start the active scan
            ApiResponse scanResponse = api.ascan.scan(url, "True", "False", null, null, null);
            String scanID = scanResponse.toString();

            while (true) {
                Thread.sleep(1000);
                ApiResponse statusResponse = api.ascan.status(scanID);
                String status = statusResponse.toString();
                if (status.equals("100")) {
                    System.out.println("Active scan completed.");
                    break;
                }
            }
            afterScan();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String afterScan(){
        if (api != null){
            String title = "Your website";
            String template = "traditional-html";
            String description = "This is a security test report for the website that you provided";
            String reportFileName = "Security-Zap-Report.html";
            String targetFolder = System.getProperty("user.dir");

            try{
                ApiResponse response = api.reports.generate(title, template, null, description,
                        null, null, null, null, null, reportFileName, null, targetFolder, null);
                String reportPath = Paths.get(targetFolder, reportFileName).toString();
                System.out.println("ZAP report generated at this location: " + response.toString());
                return reportPath;
            } catch (ClientApiException ex){
                ex.printStackTrace();
            }
        }
        return null;
    }
}
