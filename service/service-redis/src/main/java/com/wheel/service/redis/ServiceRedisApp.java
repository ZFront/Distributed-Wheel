package com.wheel.service.redis;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ServiceRedisApp {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ServiceRedisApp.class).web(WebApplicationType.NONE).run(args);
    }
}
