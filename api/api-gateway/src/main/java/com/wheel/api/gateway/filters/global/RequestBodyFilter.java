package com.wheel.api.gateway.filters.global;

import com.wheel.api.gateway.config.FilterOrderProp;
import com.wheel.api.gateway.constant.CacheKey;
import com.wheel.api.gateway.exception.ApiException;
import com.wheel.common.enums.api.ApiBizCodeEnum;
import com.wheel.common.util.JsonUtil;
import com.wheel.common.util.StringUtil;
import com.wheel.common.vo.api.RequestParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory;
import org.springframework.cloud.gateway.handler.predicate.ReadBodyPredicateFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @description body缓存及转换过滤器
 * 1、做一层对应的参数转换
 * 2、做一次缓存操作
 * 参考方法：
 * {@link ModifyRequestBodyGatewayFilterFactory}
 * {@link ReadBodyPredicateFactory}
 * @author: zhouf
 * @date: 2020/9/8
 */
@Slf4j
@Component
public class RequestBodyFilter extends AbstractGlobalFilter {

    private final List<HttpMessageReader<?>> messageReaders = HandlerStrategies.withDefaults().messageReaders();

    /**
     * 默认第一个过滤器
     *
     * @return
     */
    @Override
    public int getOrder() {
        return FilterOrderProp.REQUEST_BODY_FILTER;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.debug("{} 过滤器", this.getClass().getName());

        if (exchange.getRequest().getHeaders().getContentType() == null) {
            return chain.filter(exchange);
        }

        return DataBufferUtils.join(exchange.getRequest().getBody())
                .flatMap(dataBuffer -> {
                    DataBufferUtils.retain(dataBuffer);
                    Flux<DataBuffer> cachedFlux = Flux
                            .defer(() -> Flux.just(dataBuffer.slice(0, dataBuffer.readableByteCount())));

                    ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                        @Override
                        public Flux<DataBuffer> getBody() {
                            return cachedFlux;
                        }
                    };

                    ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
                    return ServerRequest.create(mutatedExchange, messageReaders)
                            .bodyToMono(String.class)
                            .doOnNext(objectValue -> {
                                cacheTransferBody(objectValue, exchange);
                            }).then(chain.filter(mutatedExchange));
                });
    }

    /**
     * 缓存转换后的请求内容
     *
     * @param requestBody
     * @param exchange
     */
    private void cacheTransferBody(String requestBody, ServerWebExchange exchange) {
        RequestParam requestParam;
        log.info("apiGateway requestBody={}", requestBody);
        if (StringUtil.isEmpty(requestBody)) {
            throw new ApiException(ApiBizCodeEnum.FAIL, "请求体不能为空");
        }
        try {
            requestParam = JsonUtil.toBean(requestBody, RequestParam.class);
        } catch (Exception e) {
            throw new ApiException(ApiBizCodeEnum.FAIL, "请求体格式错误");
        }

        exchange.getAttributes().put(CacheKey.API_REQUEST_PARAM, requestParam);
    }
}
