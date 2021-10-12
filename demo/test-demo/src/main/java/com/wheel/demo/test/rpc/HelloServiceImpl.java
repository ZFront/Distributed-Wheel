package com.wheel.demo.test.rpc;

/**
 * @desc
 * @author: zhouf
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String name) {
        return "Hello, " + name;
    }
}
