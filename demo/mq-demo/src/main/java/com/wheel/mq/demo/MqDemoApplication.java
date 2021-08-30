package com.wheel.mq.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class MqDemoApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(MqDemoApplication.class).run(args);
    }
}
