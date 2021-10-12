package com.wheel.api.gateway.filters.global;

import com.wheel.api.gateway.config.FilterOrderProp;
import com.wheel.api.gateway.constant.CacheKey;
import com.wheel.api.gateway.exception.ApiException;
import com.wheel.common.enums.api.ApiBizCodeEnum;
import com.wheel.common.enums.api.MethodEnum;
import com.wheel.common.enums.api.VersionEnum;
import com.wheel.common.util.JsonUtil;
import com.wheel.common.util.StringUtil;
import com.wheel.common.vo.api.RequestParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @description 参数校验过滤器
 * @author: zhouf
 * @date: 2020/9/9
 */
@Slf4j
@Component
public class RequestCheckFilter extends AbstractGlobalFilter {

    /**
     * 默认第二个过滤器, 可以针对参数做一下校验操作
     *
     * @return
     */
    @Override
    public int getOrder() {
        return FilterOrderProp.REQUEST_CHECK_FILTER;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.debug("{} 过滤器", this.getClass().getName());
        Object object = exchange.getAttributes().get(CacheKey.API_REQUEST_PARAM);
        RequestParam param = (RequestParam) object;

        // 校验参数
        paramValid(param);

        return chain.filter(exchange);
    }

    public void paramValid(RequestParam param) {
        if (param == null) {
            throw new ApiException(ApiBizCodeEnum.FAIL, "请求体不能为空");
        }
        if (StringUtil.isEmpty(param.getVersion())) {
            throw new ApiException(ApiBizCodeEnum.FAIL, "version版本号不能为空");
        }
        if (StringUtil.isEmpty(param.getMethod())) {
            throw new ApiException(ApiBizCodeEnum.FAIL, "method方法名不能为空");
        }
        if (StringUtil.isEmpty(param.getData())) {
            throw new ApiException(ApiBizCodeEnum.FAIL, "data不能为空");
        }
        if (!JsonUtil.isJson(param.getData())) {
            throw new ApiException(ApiBizCodeEnum.FAIL, "请求体格式错误");
        }
        if (VersionEnum.getEnum(param.getVersion()) == null) {
            throw new ApiException(ApiBizCodeEnum.FAIL, "version版本号格式错误");
        }
        if (MethodEnum.getEnum(param.getMethod()) == null) {
            throw new ApiException(ApiBizCodeEnum.FAIL, "method方法名格式错误");
        }

        // TODO 可以继续扩展， e.g: 如果有密钥对加密时，做auth校验等操作
    }
}
