package com.wheel.web.api.admin.controller;

import com.wheel.common.exception.BizException;
import com.wheel.common.vo.rest.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.RpcException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @desc 异常测试
 * @author: zhouf
 */
@Slf4j
@RestController
@RequestMapping("exception")
public class ExceptionDemoController {

    @RequestMapping("throwBizException")
    public RestResult throwBizException() {
        throw new BizException("BizException error !");
    }

    @RequestMapping("throwException")
    public RestResult throwException() throws Exception {
        throw new Exception("Exception error !");
    }

    @RequestMapping("throwRpcException")
    public RestResult throwRpcException() {
        throw new RpcException("RpcException error !");
    }
}
