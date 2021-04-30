package com.wheel.rpc.client.controller;

import com.wheel.rpc.client.facade.HelloService;
import com.wheel.rpc.client.facade.HiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @desc
 * @author: zhouf
 */
@Slf4j
@RestController
public class IndexController {

    @Autowired
    private HelloService helloService;

    @Autowired
    private HiService hiService;

    @RequestMapping("/hello")
    public String hello() {
        log.info("request hello start");
        return helloService.hello(1);
    }

    @RequestMapping("/hi")
    public String hi() {
        log.info("request hi start");
        return hiService.hi("june", "candy", "cat");
    }
}
