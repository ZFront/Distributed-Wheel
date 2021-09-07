package com.wheel.gateway.utils;

import com.wheel.common.util.StringUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @desc
 * @author: zhouf
 */
public class RequestUtil {

    private static final String HEADER_X_FORWARD = "x-forwarded-for";
    private static final String HEADER_PROXY_CLIENT = "Proxy-Client-IP";
    private static final String HEADER_WL_PROXY_CLIENT = "WL-Proxy-Client-IP";
    private static final String HEADER_UNKNOWN_VALUE = "unknown";
    private static final String LOCAL_IPV6 = "0:0:0:0:0:0:0:1";
    private static final String LOCAL_IPV4 = "127.0.0.1";

    /**
     * 获取客户端的IP地址
     *
     * @param request 请求参数
     * @return
     */
    public static String getIpAddr(ServerHttpRequest request) {
        String ipAddress;

        HttpHeaders httpHeaders = request.getHeaders();
        List<String> values = httpHeaders.get(HEADER_X_FORWARD);
        ipAddress = (values == null || values.size() < 1) ? "" : values.get(0);
        if (StringUtil.isEmpty(ipAddress) || HEADER_UNKNOWN_VALUE.equalsIgnoreCase(ipAddress)) {
            values = httpHeaders.get(HEADER_PROXY_CLIENT);
            ipAddress = (values == null || values.size() < 1) ? "" : values.get(0);
        }
        if (StringUtil.isEmpty(ipAddress) || HEADER_UNKNOWN_VALUE.equalsIgnoreCase(ipAddress)) {
            values = httpHeaders.get(HEADER_WL_PROXY_CLIENT);
            ipAddress = (values == null || values.size() < 1) ? "" : values.get(0);
        }
        if (StringUtil.isEmpty(ipAddress) || HEADER_UNKNOWN_VALUE.equalsIgnoreCase(ipAddress)) {
            InetSocketAddress remoteAddress = request.getRemoteAddress();
            ipAddress = remoteAddress == null ? "" : remoteAddress.getAddress().getHostAddress();
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (StringUtil.isNotEmpty(ipAddress) && ipAddress.indexOf(",") > 0) {
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
        }
        if (LOCAL_IPV6.equals(ipAddress) || StringUtil.isEmpty(ipAddress)) {
            ipAddress = LOCAL_IPV4;
        }
        return ipAddress;
    }

}
