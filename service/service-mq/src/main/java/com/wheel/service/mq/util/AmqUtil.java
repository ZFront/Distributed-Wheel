package com.wheel.service.mq.util;

import com.google.common.collect.Maps;
import com.wheel.service.mq.prop.AmqMonitorProp;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

/**
 * @desc activemq 工具类
 * @author: zhouf
 */
public class AmqUtil {

    /**
     * 是否activeMQ内部的消息
     *
     * @param destination
     * @return
     */
    public static boolean isActiveMQInnerDestination(String destination) {
        return destination != null && destination.startsWith("ActiveMQ.");
    }

    /**
     * 是否topic
     *
     * @param topic
     * @return
     */
    public static boolean isVirtualTopic(String topic) {
        return topic != null && topic.startsWith("VirtualTopic.");
    }

    /**
     * 构建鉴权请求头
     *
     * @param broker
     * @return
     */
    public static Map<String, String> getAuthHeader(AmqMonitorProp.BrokerNode broker) {
        String auth = broker.getUser() + ":" + broker.getPassword();
        Map<String, String> header = Maps.newHashMap();
        header.put("Authorization", "Basic " + Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8)));
        return header;
    }
}

