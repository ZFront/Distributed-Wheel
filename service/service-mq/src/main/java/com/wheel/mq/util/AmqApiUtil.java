package com.wheel.mq.util;

import com.wheel.common.util.OkHttpUtil;
import com.wheel.mq.prop.AmqMonitorProp;
import lombok.extern.slf4j.Slf4j;

/**
 * @desc activemq rest api 请求类
 * @author: zhouf
 */
@Slf4j
public class AmqApiUtil {

    /**
     * activeMq API地址，采用String.format进行占位符分隔
     */
    private static String AMQ_API_URL = "http://%s/api/jolokia/read/org.apache.activemq:brokerName=%s,type=Broker/%s";

    /**
     * 队列详情API地址
     */
    private static String AMQ_QUEUE_API_URL = "http://%s/api/jolokia/read/org.apache.activemq:brokerName=%s,type=Broker,destinationType=Queue,destinationName=%s";

    /**
     * 查询broker中的地址信息
     *
     * @param broker
     * @param isQueue 是否queue
     * @return
     */
    public static String qryDestination(AmqMonitorProp.BrokerNode broker, boolean isQueue) {
        String brokerIp = broker.getHost() + ":" + broker.getPort();
        String brokerName = broker.getBrokerName();
        String url = String.format(AMQ_API_URL, brokerIp, brokerName, isQueue ? "Queues" : "Topics");
        log.debug("brokerName={}, reqUrl = {}", brokerName, url);
        String body = "";
        try {
            body = OkHttpUtil.getSync(url, AmqUtil.getAuthHeader(broker), null);
        } catch (Exception e) {
            log.error("broker:{}, 请求获取内容失败:", brokerName, e);
        }
        return body;
    }

    /**
     * 查询队列信息
     *
     * @param broker
     * @param queueName
     * @return
     */
    public static String qryQueueInfo(AmqMonitorProp.BrokerNode broker, String queueName) {
        String brokerIp = broker.getHost() + ":" + broker.getPort();
        String brokerName = broker.getBrokerName();
        String url = String.format(AMQ_QUEUE_API_URL, brokerIp, brokerName, queueName);
        log.debug("brokerName={}, reqUrl = {}", brokerName, url);
        String body = "";
        try {
            body = OkHttpUtil.getSync(url, AmqUtil.getAuthHeader(broker), null);
        } catch (Exception e) {
            log.error("broker:{}, 请求获取内容失败:", brokerName, e);
        }
        return body;
    }
}
