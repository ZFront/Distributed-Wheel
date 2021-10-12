package com.wheel.demo.test.rpc;

import org.junit.Test;

/**
 * @desc
 * @author: zhouf
 */
public class TsRpc {

    @Test
    public void expose() throws Exception {
        // 提供者暴露接口
        HelloService service = new HelloServiceImpl();
        RpcFramework.expose(service, 2333);
    }

    @Test
    public void refer() {
        HelloService helloService = RpcFramework.refer(HelloService.class, "127.0.0.1", 2333);
        System.out.println(helloService.hello("rpc"));
    }
}
