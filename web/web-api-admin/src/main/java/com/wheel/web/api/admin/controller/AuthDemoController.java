package com.wheel.web.api.admin.controller;

import com.wheel.common.vo.rest.RestResult;
import com.wheel.web.api.common.annotation.Permission;
import com.wheel.web.api.common.annotation.CurrentUser;
import com.wheel.web.api.common.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Permission("auth")
    @GetMapping("noAuth")
    public RestResult noAuth() {
        return RestResult.success("操作成功");
    }

    @GetMapping("getLoginUser")
    public RestResult getLoginUser(@CurrentUser User user) {
        return RestResult.success(user);
    }
}
