package com.wheel.web.api.common.dto.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * @desc
 * @author: zhouf
 */
@Getter
@Setter
public class LoginDTO {

    /**
     * 用户名
     */
    @NotEmpty(message = "userName不能为空")
    private String userName;

    /**
     * 密码
     */
    @NotEmpty(message = "password不能为空")
    private String password;

    /**
     * 图形验证码  (二选一)
     */
    private String imgCode;

    /**
     * 短信验证码 （二选一）
     */
    private String smsCode;
}
