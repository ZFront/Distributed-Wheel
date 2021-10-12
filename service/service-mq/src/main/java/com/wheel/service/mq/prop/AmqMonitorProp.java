package com.wheel.service.mq.prop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @desc 监控配置类
 * @author: zhouf
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "monitor.activemq")
public class AmqMonitorProp {

    /**
     * 是否开启监控, 默认为 false
     */
    private boolean enabled = false;

    /**
     * 扫描集群的间隔时间
     */
    private int period = 60;

    /**
     * 不进行监控的白名单
     */
    private String whiteListQueues = "";

    private List<BrokerNode> brokerNodes;

    /**
     * brokerNode 属性
     */
    public static class BrokerNode {
        private String host;
        private String port;
        private String brokerName;
        /**
         * user
         */
        private String user = "";

        /**
         * pwd
         */
        private String password = "";



        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getPort() {
            return port;
        }

        public void setPort(String port) {
            this.port = port;
        }

        public String getBrokerName() {
            return brokerName;
        }

        public void setBrokerName(String brokerName) {
            this.brokerName = brokerName;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
