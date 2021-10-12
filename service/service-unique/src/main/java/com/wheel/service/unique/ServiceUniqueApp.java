package com.wheel.service.unique;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ServiceUniqueApp {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ServiceUniqueApp.class).web(WebApplicationType.NONE).run(args);
    }
}

