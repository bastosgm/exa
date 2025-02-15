package com.exa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication(scanBasePackages = "com.exa")
public class MyApp {
    private static final Logger logger = LoggerFactory.getLogger(MyApp.class);
    public static void main(String[] args) {
        logger.info("Starting application at PORT 8080");
        SpringApplication.run(MyApp.class, args);
    }
}
