package com.wheel.demo.sharding;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class DemoShardingApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DemoShardingApplication.class).run(args);
    }
}
