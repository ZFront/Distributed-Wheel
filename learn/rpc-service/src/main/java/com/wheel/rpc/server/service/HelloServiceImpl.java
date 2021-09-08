package com.wheel.rpc.server.service;

/**
 * @desc
 * @author: zhouf
 */
public class HelloServiceImpl {

    public String hello(Integer num) {
        System.out.println("hello: " + num);
        return "hello end";
    }
}
