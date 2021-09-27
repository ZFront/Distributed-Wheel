package com.wheel.demo.controller;

import com.wheel.common.vo.api.ResponseResult;
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
    public ResponseResult limit() {
        if (LimiterConfig.rateLimiter.tryAcquire()) {
            return ResponseResult.success();
        } else {
            return ResponseResult.fail("服务器繁忙！");
        }
    }
}
