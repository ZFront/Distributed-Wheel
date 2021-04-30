package com.wheel.rpc.server.service;

/**
 * @desc
 * @author: zhouf
 */
public class HiServiceImpl {

    public String hi(String... content) {
        for (String str : content) {
            System.out.println("hi: " + str);
        }
        return "hi end";
    }
}
