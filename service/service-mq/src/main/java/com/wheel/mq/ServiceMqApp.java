package com.wheel.mq;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ServiceMqApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ServiceMqApp.class).web(WebApplicationType.NONE).run(args);
    }
}
