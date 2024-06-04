package com.example.vscanner.Business.MyScanService;

import com.example.vscanner.Business.Exceptions.ScanNotFoundException;
import com.example.vscanner.Domain.Scan;
import com.example.vscanner.Domain.ScanRR.ScanResponse;
import com.example.vscanner.Persistence.Entity.ScanEntity;
import com.example.vscanner.Persistence.Entity.UserEntity;
import com.example.vscanner.Persistence.Entity.VulnerabilityEntity;
import com.example.vscanner.Persistence.ScanRepository;
import com.example.vscanner.Persistence.UserRepository;
import com.example.vscanner.Persistence.VulnerabilityRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.net.URIBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@AllArgsConstructor
@Service
@Transactional
public class MyScanService {
    private ScanRepository scanRepository;
    private UserRepository userRepository;
    private VulnerabilityRepository vulnerabilityRepository;

    public ScanResponse scanForSqlInjection(String url, Long userId) {

        Optional<UserEntity> userEntity = userRepository.findById(userId);
        try {
            Document doc = Jsoup.connect(url).get();
            Elements forms = doc.select("form");
            List<VulnerabilityEntity> vulnerabilityEntities = new ArrayList<>();

            for (Element form : forms) {
                String formAction = form.attr("action");
                String formMethod = form.attr("method").toUpperCase();
                Elements inputs = form.select("input[name]");

                for (Element input : inputs) {
                    String inputName = input.attr("name");

                    boolean sqlInjectFound = false;
                    boolean cmdInjectFound = false;
                    boolean xssFound = false;
                    boolean pathTravFound = false;

                    boolean isVulnerable = testSqlInjection(formAction, formMethod, inputName, url);
                    if (isVulnerable) {
                        System.out.println("The application is vulnerable to SQL Injection");
                        VulnerabilityEntity vulnerabilityEntity = VulnerabilityEntity.builder()
                                .type("SQL Injection")
                                .description("SQL Injection vulnerability detected")
                                .affectedUrl(url)
                                .riskLevel(10)
                                .riskAssessment("Critical")
                                .mitigation("Sanitize inputs and use prepared statements.")
                                .build();

                        vulnerabilityEntities.add(vulnerabilityEntity);
                        sqlInjectFound = true;
                    }

                    boolean isXssVulnerable = testXSS(formAction, formMethod, inputName, url);
                    if (isXssVulnerable) {
                        System.out.println("The application is vulnerable to Cross-Site Scripting (XSS)");
                        VulnerabilityEntity vulnerabilityEntity = VulnerabilityEntity.builder()
                                .type("Cross-Site Scripting (XSS)")
                                .description("XSS vulnerability detected")
                                .affectedUrl(url)
                                .riskLevel(8)
                                .riskAssessment("High")
                                .mitigation("Sanitize inputs and escape output properly.")
                                .build();

                        vulnerabilityEntities.add(vulnerabilityEntity);
                        xssFound = true;
                    }

                    boolean isPathTraversalVulnerable = testPathTraversal(formAction, formMethod, inputName, url);
                    if (isPathTraversalVulnerable) {
                        System.out.println("The application is vulnerable to Path Traversal");
                        VulnerabilityEntity vulnerabilityEntity = VulnerabilityEntity.builder()
                                .type("Path Traversal")
                                .description("Path Traversal vulnerability detected")
                                .affectedUrl(url)
                                .riskLevel(9)
                                .riskAssessment("High")
                                .mitigation("Validate and sanitize file paths.")
                                .build();


                        vulnerabilityEntities.add(vulnerabilityEntity);
                        pathTravFound = true;

                    }

                    boolean isCommandInjectionVulnerable = testCommandInjection(formAction, formMethod, inputName, url);
                    if (isCommandInjectionVulnerable) {
                        System.out.println("The application is vulnerable to Command Injection");
                        VulnerabilityEntity vulnerabilityEntity = VulnerabilityEntity.builder()
                                .type("Command Injection")
                                .description("Command Injection vulnerability detected")
                                .affectedUrl(url)
                                .riskLevel(10)
                                .riskAssessment("Critical")
                                .mitigation("Validate and sanitize input parameters.")
                                .build();

                        vulnerabilityEntities.add(vulnerabilityEntity);
                        cmdInjectFound = true;
                    }

                    if (!vulnerabilityEntities.isEmpty()) {
                        ScanEntity scanEntity = ScanEntity.builder()
                                .user(userEntity.get())
                                .url(url)
                                .vulnerabilities(vulnerabilityEntities)
                                .build();

                        VulnerabilityEntity sqlInject = initiateSqlInject(url);
                        VulnerabilityEntity cmdInject = initiateCommandInject(url);
                        VulnerabilityEntity xss = initiateXss(url);
                        VulnerabilityEntity pathTrav = initiatePathTraversal(url);

                        vulnerabilityEntities.add(sqlInject);
                        vulnerabilityEntities.add(cmdInject);
                        vulnerabilityEntities.add(xss);
                        vulnerabilityEntities.add(pathTrav);

                        scanRepository.save(scanEntity);

                        for (VulnerabilityEntity vulnerabilityEntity : vulnerabilityEntities) {
                            vulnerabilityEntity.setScan(scanEntity);
                        }
                        vulnerabilityRepository.saveAll(vulnerabilityEntities);

                        return ScanResponse.builder().id(scanEntity.getId()).build();
                    }
                    else {
                        ScanEntity scanEntity = ScanEntity.builder()
                                .user(userEntity.get())
                                .url(url)
                                .vulnerabilities(new ArrayList<>())
                                .build();

                        System.out.println("The application is safe from SQL Injection, XSS, Path Traversal and Command Injection.");

                        if (!sqlInjectFound){
                            VulnerabilityEntity sqlInject = initiateSqlInject(url);
                            vulnerabilityEntities.add(sqlInject);
                        }

                        if (!xssFound){
                            VulnerabilityEntity xss = initiateXss(url);
                            vulnerabilityEntities.add(xss);
                        }

                        if (!pathTravFound) {
                            VulnerabilityEntity pathTrav = initiatePathTraversal(url);
                            vulnerabilityEntities.add(pathTrav);
                        }

                        if (!cmdInjectFound){
                            VulnerabilityEntity cmdInject = initiateCommandInject(url);
                            vulnerabilityEntities.add(cmdInject);
                        }

                        scanRepository.save(scanEntity);
                        for (VulnerabilityEntity vulnerabilityEntity : vulnerabilityEntities) {
                            vulnerabilityEntity.setScan(scanEntity);
                        }
                        vulnerabilityRepository.saveAll(vulnerabilityEntities);
                        return ScanResponse.builder().id(scanEntity.getId()).build();
                    }
                }
            }
        } catch (IOException io) {
            io.printStackTrace();
        }

        return ScanResponse.builder().id(null).build();
    }

    private boolean testSqlInjection(String formAction, String formMethod, String paramName, String url) {
        String[] payloads = {
                "' OR '1'='1",
                "' OR '1'='1' -- ",
                "' OR '1'='1' /*",
                "' OR '1'='2"
        };

        Map<String, String> responseMap = new HashMap<>();

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String targetUrl = url.endsWith("/") ? url + formAction : url + "/" + formAction;

            for (String payload : payloads) {
                String paramValue = URLEncoder.encode(payload, StandardCharsets.UTF_8.toString());
                if ("GET".equalsIgnoreCase(formMethod)) {
                    URIBuilder uriBuilder = new URIBuilder(targetUrl);
                    uriBuilder.addParameter(paramName, paramValue);
                    HttpGet get = new HttpGet(uriBuilder.build());

                    try (CloseableHttpResponse response = client.execute(get)) {
                        String responseBody = EntityUtils.toString(response.getEntity());
                        responseMap.put(payload, responseBody);

                        // Check for typical SQL injection error messages
                        if (responseBody.contains("syntax error") || responseBody.contains("SQL") || responseBody.contains("database") || responseBody.toLowerCase().contains("error")) {
                            return true;
                        }
                    }
                } else if ("POST".equalsIgnoreCase(formMethod)) {
                    HttpPost post = new HttpPost(targetUrl);
                    StringEntity entity = new StringEntity(paramName + "=" + paramValue);
                    post.setEntity(entity);
                    post.setHeader("Content-Type", "application/x-www-form-urlencoded");

                    try (CloseableHttpResponse response = client.execute(post)) {
                        String responseBody = EntityUtils.toString(response.getEntity());
                        responseMap.put(payload, responseBody);

                        // Check for typical SQL injection error messages
                        if (responseBody.contains("syntax error") || responseBody.contains("SQL") || responseBody.contains("database") || responseBody.toLowerCase().contains("error")) {
                            return true;
                        }
                    }
                }
            }

            // Compare responses of different payloads to detect logical differences
            String trueResponse = responseMap.get("' OR '1'='1");
            String falseResponse = responseMap.get("' OR '1'='2");
            if (trueResponse != null && falseResponse != null && !trueResponse.equals(falseResponse)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean testXSS(String formAction, String formMethod, String paramName, String url) {
        String[] payloads = {
                "<script>alert('XSS')</script>",
                "\"><script>alert('XSS')</script>",
                "'\"><script>alert('XSS')</script>"
        };

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String targetUrl = url.endsWith("/") ? url + formAction : url + "/" + formAction;

            for (String payload : payloads) {
                String paramValue = URLEncoder.encode(payload, StandardCharsets.UTF_8);
                if ("GET".equalsIgnoreCase(formMethod)) {
                    URIBuilder uriBuilder = new URIBuilder(targetUrl);
                    uriBuilder.addParameter(paramName, paramValue);
                    HttpGet get = new HttpGet(uriBuilder.build());

                    try (CloseableHttpResponse response = client.execute(get)) {
                        String responseBody = EntityUtils.toString(response.getEntity());

                        if (responseBody.contains(payload)) {
                            return true;
                        }
                    }
                } else if ("POST".equalsIgnoreCase(formMethod)) {
                    HttpPost post = new HttpPost(targetUrl);
                    StringEntity entity = new StringEntity(paramName + "=" + paramValue);
                    post.setEntity(entity);
                    post.setHeader("Content-Type", "application/x-www-form-urlencoded");

                    try (CloseableHttpResponse response = client.execute(post)) {
                        String responseBody = EntityUtils.toString(response.getEntity());

                        if (responseBody.contains(payload)) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean testPathTraversal(String formAction, String formMethod, String paramName, String url) {
        String[] payloads = {
                "../../../../etc/passwd",
                "../../../../../../../../windows/win.ini",
                "../../../../../../../../boot.ini",
                "../../../../../../../../etc/shadow"
        };

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String targetUrl = url.endsWith("/") ? url + formAction : url + "/" + formAction;

            for (String payload : payloads) {
                String paramValue = URLEncoder.encode(payload, StandardCharsets.UTF_8);
                if ("GET".equalsIgnoreCase(formMethod)) {
                    URIBuilder uriBuilder = new URIBuilder(targetUrl);
                    uriBuilder.addParameter(paramName, paramValue);
                    HttpGet get = new HttpGet(uriBuilder.build());

                    try (CloseableHttpResponse response = client.execute(get)) {
                        String responseBody = EntityUtils.toString(response.getEntity());

                        // Check if the response contains sensitive file content
                        if (responseBody.contains("root:") || responseBody.contains("[boot loader]") || responseBody.contains("[boot description]") || responseBody.contains("shadow:")) {
                            return true;
                        }
                    }
                } else if ("POST".equalsIgnoreCase(formMethod)) {
                    HttpPost post = new HttpPost(targetUrl);
                    StringEntity entity = new StringEntity(paramName + "=" + paramValue);
                    post.setEntity(entity);
                    post.setHeader("Content-Type", "application/x-www-form-urlencoded");

                    try (CloseableHttpResponse response = client.execute(post)) {
                        String responseBody = EntityUtils.toString(response.getEntity());

                        // Check if the response contains sensitive file content
                        if (responseBody.contains("root:") || responseBody.contains("[boot loader]") || responseBody.contains("[boot description]") || responseBody.contains("shadow:")) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ScanNotFoundException("Something went wrong");
        }

        return false;
    }

    private boolean testCommandInjection(String formAction, String formMethod, String paramName, String url) {
        String[] payloads = {
                "; ls",
                "|| ls",
                "| ls",
                "& ls"
        };

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String targetUrl = url.endsWith("/") ? url + formAction : url + "/" + formAction;

            for (String payload : payloads) {
                String paramValue = URLEncoder.encode(payload, StandardCharsets.UTF_8.toString());
                if ("GET".equalsIgnoreCase(formMethod)) {
                    URIBuilder uriBuilder = new URIBuilder(targetUrl);
                    uriBuilder.addParameter(paramName, paramValue);
                    HttpGet get = new HttpGet(uriBuilder.build());

                    try (CloseableHttpResponse response = client.execute(get)) {
                        String responseBody = EntityUtils.toString(response.getEntity());
                        if (responseBody.contains("bin") || responseBody.contains("etc") || responseBody.contains("usr")) {
                            return true;
                        }
                    }
                } else if ("POST".equalsIgnoreCase(formMethod)) {
                    HttpPost post = new HttpPost(targetUrl);
                    StringEntity entity = new StringEntity(paramName + "=" + paramValue);
                    post.setEntity(entity);
                    post.setHeader("Content-Type", "application/x-www-form-urlencoded");

                    try (CloseableHttpResponse response = client.execute(post)) {
                        String responseBody = EntityUtils.toString(response.getEntity());
                        if (responseBody.contains("bin") || responseBody.contains("etc") || responseBody.contains("usr")) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private VulnerabilityEntity initiateSqlInject(String url){
        VulnerabilityEntity sqlInject = VulnerabilityEntity.builder()
                .type("SQL Injection")
                .description("SQL Injection vulnerability not detected")
                .affectedUrl(url)
                .riskLevel(0)
                .riskAssessment("Safe")
                .mitigation("Wasn't able to detect a weakness using sql injection techniques, use Zap service in order to confirm that using this scan.")
                .build();

        return sqlInject;
    }

    private VulnerabilityEntity initiateCommandInject(String url){
        VulnerabilityEntity cmdInject = VulnerabilityEntity.builder()
                .type("Command Injection")
                .description("Command Injection vulnerability not detected")
                .affectedUrl(url)
                .riskLevel(0)
                .riskAssessment("Safe")
                .mitigation("Wasn't able to detect a weakness using command injection techniques, use Zap service in order to confirm that using this scan.")
                .build();

        return cmdInject;
    }

    private VulnerabilityEntity initiateXss(String url){
        VulnerabilityEntity xss = VulnerabilityEntity.builder()
                .type("Cross-Site Scripting (XSS)")
                .description("XSS vulnerability not detected")
                .affectedUrl(url)
                .riskLevel(0)
                .riskAssessment("Safe")
                .mitigation("Wasn't able to detect a weakness using XSS techniques, use Zap service in order to confirm that using this scan.")
                .build();

        return xss;
    }

    private VulnerabilityEntity initiatePathTraversal(String url){
        VulnerabilityEntity pathTrav = VulnerabilityEntity.builder()
                .type("Path Traversal")
                .description("Path Traversal vulnerability not detected")
                .affectedUrl(url)
                .riskLevel(0)
                .riskAssessment("Safe")
                .mitigation("Wasn't able to detect a weakness using path traversal techniques, use Zap service in order to confirm that using this scan.")
                .build();

        return pathTrav;
    }
}
