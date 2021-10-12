package com.wheel.api.gateway.filters.global;

import com.wheel.api.gateway.config.FilterOrderProp;
import com.wheel.common.util.JsonUtil;
import com.wheel.common.util.StringUtil;
import com.wheel.common.vo.api.ResponseParam;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR;

/**
 * @description 返回类重新构建并返回
 * 参考的例子 {@link ModifyResponseBodyGatewayFilterFactory}
 * @author: zhouf
 * @date: 2020/9/9
 */
@Slf4j
@Component
public class ResponseBuildFilter extends AbstractGlobalFilter {

    /**
     * response 过滤器
     *
     * @return
     */
    @Override
    public int getOrder() {
        return FilterOrderProp.RESPONSE_BUILD_FILTER;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.debug("{} 过滤器", this.getClass().getName());
        return chain.filter(exchange.mutate().response(decorate(exchange)).build());
    }

    ServerHttpResponse decorate(ServerWebExchange exchange) {
        return new ServerHttpResponseDecorator(exchange.getResponse()) {

            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                String originalResponseContentType = exchange.getAttribute(ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR);
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.add(HttpHeaders.CONTENT_TYPE, originalResponseContentType);

                ClientResponse clientResponse = ClientResponse.create(exchange.getResponse().getStatusCode())
                        .headers(headers -> headers.putAll(httpHeaders))
                        .body(Flux.from(body)).build();

                Mono modifiedBody = clientResponse.bodyToMono(String.class)
                        .flatMap(originalBody -> {
                            // 这里的originalBody指的是服务直接返回的数据
                            log.debug("原服务返回数据为：{}", originalBody);
                            String respBody = formatResponse(originalBody);
                            log.debug("网关返回数据为：{}", respBody);
                            return Mono.just(respBody);
                        });

                BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, String.class);
                CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, exchange.getResponse().getHeaders());

                return bodyInserter.insert(outputMessage, new BodyInserterContext())
                        .then(Mono.defer(() -> {
                            Flux<DataBuffer> messageBody = outputMessage.getBody();
                            HttpHeaders headers = getDelegate().getHeaders();
                            if (!headers.containsKey(HttpHeaders.TRANSFER_ENCODING)) {
                                messageBody = messageBody.doOnNext(data -> headers
                                        .setContentLength(data.readableByteCount()));
                            }
                            return getDelegate().writeWith(messageBody);
                        }));
            }
        };
    }

    /**
     * 格式化原信息
     *
     * @param originalBody
     * @return
     */
    private String formatResponse(String originalBody) {
        ResponseParam responseParam;
        try {
            responseParam = JsonUtil.toBean(originalBody, ResponseParam.class);
        } catch (Exception e) {
            // 如果解析失败，说明下游服务未按照当前规范返回，直接返回对应的内容
            return originalBody;
        }
        // 如果应答码为空
        if (StringUtil.isEmpty(responseParam.getResp_code())) {
            return JsonUtil.toString(ResponseParam.acceptUnknown());
        }

        // TODO 可以添加其他操作, e.g: 对响应体加密等

        return JsonUtil.toString(responseParam);
    }
}
