package com.wheel.lock;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ServiceLockApp {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ServiceLockApp.class).web(WebApplicationType.NONE).run(args);
    }
}
