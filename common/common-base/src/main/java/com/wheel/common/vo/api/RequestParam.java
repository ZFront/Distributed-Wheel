package com.wheel.common.vo.api;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @description api gateway 请求体
 * @author: zhouf
 * @date: 2020/9/7
 */
@Getter
@Setter
public class RequestParam implements Serializable {

    /**
     * {@link com.wheel.gateway.enums.VersionEnum}
     */
    private String version;

    /**
     * {@link com.wheel.gateway.enums.MethodEnum}
     */
    private String method;

    /**
     * 要求传递Json格式
     */
    private String data;
}
