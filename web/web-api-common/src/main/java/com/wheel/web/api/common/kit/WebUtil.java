package com.wheel.web.api.common.kit;

import com.wheel.common.util.StringUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @desc web 工具类
 * @author: zhouf
 */
public class WebUtil {

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
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader(HEADER_X_FORWARD);
        if (StringUtil.isEmpty(ipAddress) || HEADER_UNKNOWN_VALUE.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(HEADER_PROXY_CLIENT);
        }
        if (StringUtil.isEmpty(ipAddress) || HEADER_UNKNOWN_VALUE.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(HEADER_WL_PROXY_CLIENT);
        }
        if (StringUtil.isEmpty(ipAddress) || HEADER_UNKNOWN_VALUE.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
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
