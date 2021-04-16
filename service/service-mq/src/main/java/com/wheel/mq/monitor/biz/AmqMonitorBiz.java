package com.wheel.mq.monitor.biz;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wheel.common.util.JsonUtil;
import com.wheel.common.util.StringUtil;
import com.wheel.mq.monitor.entity.QueueInfo;
import com.wheel.mq.monitor.event.BacklogEvent;
import com.wheel.mq.monitor.event.ConsumeEvent;
import com.wheel.mq.prop.AmqMonitorProp;
import com.wheel.mq.util.AmqApiUtil;
import com.wheel.mq.util.AmqUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @desc activeMq 监控业务类
 * 请求API, 参考{@see https://activemq.apache.org/rest}
 * @author: zhouf
 */
@Slf4j
@Component
public class AmqMonitorBiz {

    /**
     * 需要忽略的队列
     */
    private List<String> omitQueues = new ArrayList<>();

    @Autowired
    private ApplicationEventPublisher publisher;

    /**
     * 对队列做一次监控
     */
    public void doBrokerQueuesMonitor(List<AmqMonitorProp.BrokerNode> brokerNodes) {
        for (AmqMonitorProp.BrokerNode node : brokerNodes) {
            String brokerIp = node.getHost() + ":" + node.getPort();
            log.info("node: brokerName{}:{},开始监控任务", node.getBrokerName(), brokerIp);

            this.queueMonitor(node);
        }
    }

    /**
     * 队列监听
     *
     * @param broker
     */
    private void queueMonitor(AmqMonitorProp.BrokerNode broker) {
        // 消息积压的队列
        List<QueueInfo> backlogList = new ArrayList<>();
        // 无消费者队列
        List<QueueInfo> noConsumeList = new ArrayList<>();

        HashSet<String> queueSet = getDestination(broker, true);
        if (CollectionUtils.isEmpty(queueSet)) {
            return;
        }

        log.info("broker:{}, queueSize:{}", broker.getBrokerName(), queueSet.size());

        for (String queueName : queueSet) {

            String body = AmqApiUtil.qryQueueInfo(broker, queueName);

            if (StringUtil.isEmpty(body)) {
                continue;
            }

            HashMap<String, Object> data = JsonUtil.toBean(body, HashMap.class);
            Object value = data.get("value");
            if (value == null) {
                log.error("队列内容中的value为空 queueName={} queueInfo={}", queueName, body);
                continue;
            }

            JSONObject obj = (JSONObject) value;
            // 队列消息数量
            long queueSize = obj.getLongValue("QueueSize");
            // 生产者数量
            long producerCount = obj.getLongValue("ProducerCount");
            // 消费者数
            long consumerCount = obj.getLongValue("ConsumerCount");
            // 入队消息总数
            long enqueueCount = obj.getLongValue("EnqueueCount");
            // 出队消息总数
            long dequeueCount = obj.getLongValue("DequeueCount");
            // 消费者是否被暂停
            boolean paused = obj.getBooleanValue("Paused");

            QueueInfo queueInfo = new QueueInfo();
            queueInfo.setBrokerName(broker.getBrokerName());
            queueInfo.setQueueName(queueName);
            queueInfo.setQueueSize(queueSize);
            queueInfo.setProducerCount(producerCount);
            queueInfo.setConsumerCount(consumerCount);
            queueInfo.setEnqueueCount(enqueueCount);
            queueInfo.setDequeueCount(dequeueCount);
            queueInfo.setPaused(paused);

            // 消息队列数量>0， 且消费者数量<=0
            if (queueSize > 0 && consumerCount <= 0) {
                noConsumeList.add(queueInfo);
            }
            // 消息积压队列
            if (queueSize > 10) {
                backlogList.add(queueInfo);
            }

        }

        // 消息积压事件
        if (!CollectionUtils.isEmpty(backlogList)) {
            log.info("出现消息积压，执行积压事件发布 --> {}", broker);
            publisher.publishEvent(new BacklogEvent((backlogList)));
        }

        // 无消费者事件
        if (!CollectionUtils.isEmpty(noConsumeList)) {
            log.info("出现无消费者，执行无消费者事件发布 --> {}", broker);
            publisher.publishEvent(new ConsumeEvent((noConsumeList)));
        }

    }

    private HashSet<String> getDestination(AmqMonitorProp.BrokerNode broker, boolean isQueue) {
        HashSet<String> destNameSet = new LinkedHashSet<>();
        String brokerName = broker.getBrokerName();

        String body = AmqApiUtil.qryDestination(broker, isQueue);
        if (StringUtil.isEmpty(body)) {
            log.error("broker:{}, 获取内容为空", brokerName);
            return destNameSet;
        }

        HashMap<String, Object> valueMap = JsonUtil.toBean(body, HashMap.class);
        JSONArray valueArray = valueMap.get("value") == null ? null : (JSONArray) valueMap.get("value");
        if (valueArray == null) {
            log.error("broker：{}，获取的value内容为空", brokerName);
            return destNameSet;
        }

        Iterator<Object> iterator = valueArray.iterator();
        while (iterator.hasNext()) {
            JSONObject obj = (JSONObject) iterator.next();
            Map<String, String> map = objectNameToMap(obj.getString("objectName"));
            String destName = map.get("destinationName");
            //过滤掉这几种类型的队列：2、配置中指定忽略的队列；2、ActiveMQ自定义的一些queue和topic；3、非VirtualTopic的Topic
            if (omitQueues.contains(destName) || AmqUtil.isActiveMQInnerDestination(destName)) {
                continue;
            } else if (!isQueue && !AmqUtil.isVirtualTopic(destName)) {
                //如果是Topic类型的队列，只处理虚拟队列
                continue;
            }
            destNameSet.add(destName);
        }

        return destNameSet;
    }

    private Map<String, String> objectNameToMap(String objectName) {
        Map<String, String> map = new HashMap<>();
        if (StringUtil.isEmpty(objectName)) {
            return map;
        }
        String[] elements = objectName.split(",");
        for (String name : elements) {
            String[] kvArr = name.split("=");
            map.put(kvArr[0], kvArr[1]);
        }
        return map;
    }
}
