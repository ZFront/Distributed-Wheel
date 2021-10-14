package com.wheel.web.api.admin;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * 引入api-common包，及当前包
 */
@SpringBootApplication(scanBasePackages = {"com.wheel.web.api.common", "com.wheel.web.api.admin"})
public class WebAdminApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(WebAdminApplication.class).web(WebApplicationType.SERVLET).run(args);
    }
}
