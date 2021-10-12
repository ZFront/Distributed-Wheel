package com.wheel.api.gateway.fallback;

import com.wheel.api.gateway.constant.CacheKey;
import com.wheel.common.vo.api.ResponseParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @description hystrix的熔断、降级调用的controller
 * @author: zhouf
 */
@Slf4j
@RestController
public class FallbackController {


    @RequestMapping("/fallback")
    public Mono<Object> fallback(ServerWebExchange exchange) {
        String path = (String) exchange.getAttributes().get(CacheKey.API_REWRITE_REQUEST_PATH);
        log.debug("path={}, 进入熔断", path);
        return Mono.just(ResponseParam.acceptUnknown());
    }
}
