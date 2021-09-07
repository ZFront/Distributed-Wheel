package com.wheel.gateway.filters.local;

import com.wheel.common.enums.api.ApiBizCodeEnum;
import com.wheel.gateway.exception.ApiException;
import com.wheel.gateway.utils.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

/**
 * @desc ip 校验过滤器，非全局过滤器.这里仅做一个简单的demo
 * 可以通过在配置文件中配置后使用
 * @author: zhouf
 */
@Slf4j
public class IPCheckGatewayFilterFactory extends AbstractGatewayFilterFactory<IPCheckGatewayFilterFactory.Config> {

    /**
     * 加载配置，不然会找不到
     */
    public IPCheckGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            // 校验IP
            validIp(exchange, config);

            return chain.filter(exchange);
        };
    }

    private void validIp(ServerWebExchange exchange, Config config) {
        ServerHttpRequest request = exchange.getRequest();
        String ip = RequestUtil.getIpAddr(request);
        log.info("clientIp={}", ip);
        String blackList = config.getBlackList();

        if (blackList.contains(ip)) {
            throw new ApiException(ApiBizCodeEnum.FAIL, "IP已被限制请求");
        }
    }


    public static class Config {
        private String blackList;

        public String getBlackList() {
            return blackList;
        }

        public void setBlackList(String blackList) {
            this.blackList = blackList;
        }
    }
}
