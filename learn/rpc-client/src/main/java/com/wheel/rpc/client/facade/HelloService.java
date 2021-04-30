package com.wheel.rpc.client.facade;

import com.wheel.rpc.client.core.RemoteClass;

/**
 * @desc
 * @author: zhouf
 */
@RemoteClass("com.wheel.rpc.server.service.HelloServiceImpl")
public interface HelloService {

    String hello(Integer num);
}
