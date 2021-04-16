package com.wheel.mq.trace;

import com.wheel.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerFilter;
import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.command.Message;

/**
 * @desc 消息轨迹扩展
 * @author: zhouf
 */
@Slf4j
public class TraceBroker extends BrokerFilter {

    public TraceBroker(Broker next) {
        super(next);
    }

    /**
     * 重写send方法，发送后，发送一条消息轨迹
     *
     * @param producerExchange
     * @param messageSend
     * @throws Exception
     */
    @Override
    public void send(ProducerBrokerExchange producerExchange, Message messageSend) throws Exception {
        super.send(producerExchange, messageSend);
        log.info("{} 发送MQ", JsonUtil.toString(messageSend));
    }
}
