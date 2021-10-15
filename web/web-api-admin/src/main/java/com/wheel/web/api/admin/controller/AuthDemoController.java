package com.wheel.web.api.admin.controller;

import com.wheel.common.vo.rest.RestResult;
import com.wheel.web.api.common.annotation.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @desc
 * @author: zhouf
 */
@Slf4j
@RestController
@RequestMapping("auth")
public class AuthDemoController {

    @Auth
    public RestResult noAuth() {
        return RestResult.success("操作成功");
    }
}
