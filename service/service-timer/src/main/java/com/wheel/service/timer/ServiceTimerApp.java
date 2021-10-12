package com.wheel.service.timer;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ServiceTimerApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ServiceTimerApp.class).web(WebApplicationType.NONE).run(args);
    }
}
