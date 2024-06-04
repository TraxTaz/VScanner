package com.example.vscanner;

import com.example.vscanner.Business.MyScanService.MyScanService;
import com.example.vscanner.Domain.ScanURL;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class VScannerApplication {

    public static void main(String[] args) {

        SpringApplication.run(VScannerApplication.class, args);
    }

    @Bean
    public FlywayMigrationStrategy repair(){
        return flyway -> {
            flyway.repair();
            flyway.migrate();
        };
    }

}
