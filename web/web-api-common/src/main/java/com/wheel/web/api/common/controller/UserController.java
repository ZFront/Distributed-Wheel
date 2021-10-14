package com.wheel.web.api.common.controller;

import com.wheel.common.util.StringUtil;
import com.wheel.common.vo.rest.RestResult;
import com.wheel.web.api.common.config.WebApiProp;
import com.wheel.web.api.common.dto.user.LoginDTO;
import com.wheel.web.api.common.kit.TokenManager;
import com.wheel.web.api.common.kit.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @desc
 * @author: zhouf
 */
@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private WebApiProp webApiProp;

    @Autowired
    private TokenManager tokenManager;

    /**
     * 登录
     *
     * @param loginDTO
     * @return
     */
    @RequestMapping("login")
    public RestResult login(HttpServletRequest request, @RequestBody LoginDTO loginDTO) {
        loginDTO.setUserName(loginDTO.getUserName().trim());

        // 1、校验验证码
        if (webApiProp.getCodeType() == 1) {
            if (StringUtil.isEmpty(loginDTO.getImgCode())) {
                return RestResult.error("验证码不能为空");
            }
            // TODO
        }
        if (webApiProp.getCodeType() == 2) {
            if (StringUtil.isEmpty(loginDTO.getSmsCode())) {
                return RestResult.error("验证码不能为空");
            }
            // TODO
        }

        // 2、校验账号密码准确性

        // 3、生成 token
        String requestIp = WebUtil.getIpAddr(request);
        String userAgent = request.getHeader("User-Agent");
        String token = tokenManager.createToken(loginDTO.getUserName(), requestIp, userAgent);
        return RestResult.success(token);
    }

}
