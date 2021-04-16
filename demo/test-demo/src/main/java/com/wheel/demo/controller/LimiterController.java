package com.wheel.demo.controller;

import com.wheel.common.vo.api.ResponseParamVo;
import com.wheel.redis.ratelimit.LimiterConfig;
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
@RequestMapping("/limiter")
public class LimiterController {

    @GetMapping("/limit")
    public ResponseParamVo limit() {
        if (LimiterConfig.rateLimiter.tryAcquire()) {
            return ResponseParamVo.success();
        } else {
            return ResponseParamVo.fail("服务器繁忙！");
        }
    }
}
