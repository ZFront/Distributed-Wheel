package com.wheel.api.gateway.filters.global;

import com.wheel.api.gateway.config.FilterOrderProp;
import com.wheel.api.gateway.constant.CacheKey;
import com.wheel.common.vo.api.RequestParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.RewritePathGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * @description 重写路径Filter
 * 参考例子 {@link RewritePathGatewayFilterFactory}
 * @author: zhouf
 * @date: 2020/9/10
 */
@Slf4j
@Component
public class RewritePathFilter extends AbstractGlobalFilter {

    /**
     * 默认第三个过滤器
     *
     * @return
     */
    @Override
    public int getOrder() {
        return FilterOrderProp.REWRITE_PATH_FILTER;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.debug("{} 过滤器", this.getClass().getName());
        RequestParam requestParam = (RequestParam) exchange.getAttributes().get(CacheKey.API_REQUEST_PARAM);

        ServerHttpRequest request = exchange.getRequest();
        String orgPath = request.getURI().getPath();
        log.debug("orgPath = {}", orgPath);
        String newPath = transferPath(requestParam.getMethod());
        log.info("newPath = {}", newPath);
        ServerHttpRequest newRequest = request.mutate().path(newPath).build();

        exchange.getAttributes().put(CacheKey.API_REWRITE_REQUEST_PATH, newPath);
        return chain.filter(exchange.mutate().request(newRequest).build());
    }

    /**
     * 对method转换为对应的请求, 在发起请求
     *
     * @param method
     * @return
     */
    private String transferPath(String method) {
        String url = method.replace(".", "/");
        return "/" + url;
    }
}
