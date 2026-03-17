package com.coderhigher.auth;

import com.coderhigher.auth.config.FrontendProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableConfigurationProperties(FrontendProperties.class)
@SpringBootApplication
public class CoderHigherAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoderHigherAuthApplication.class, args);
    }
}
