package com.wheel.api.gateway.ratelimit.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @desc IP 限流器
 * @author: zhouf
 */
@Slf4j
public class IpKeyResolver implements KeyResolver {

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        log.info("current ip is:{}", exchange.getRequest().getRemoteAddress().getHostName());
        return Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }
}
