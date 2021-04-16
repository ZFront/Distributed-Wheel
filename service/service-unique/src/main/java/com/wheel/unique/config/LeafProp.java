package com.wheel.unique.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description 美团配置
 * @author: zhouf
 * @date: 2020/7/27
 */
@ConfigurationProperties(prefix = "leaf")
public class LeafProp {

    private Segment segment = new Segment();
    private SnowFlake snowflake = new SnowFlake();

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    public SnowFlake getSnowflake() {
        return snowflake;
    }

    public void setSnowflake(SnowFlake snowflake) {
        this.snowflake = snowflake;
    }

    public class Segment {
        private Boolean enable = false;
        private String jdbcUrl;
        private String jdbcUsername;
        private String jdbcPassword;

        public Boolean getEnable() {
            return enable;
        }

        public void setEnable(Boolean enable) {
            this.enable = enable;
        }

        public String getJdbcUrl() {
            return jdbcUrl;
        }

        public void setJdbcUrl(String jdbcUrl) {
            this.jdbcUrl = jdbcUrl;
        }

        public String getJdbcUsername() {
            return jdbcUsername;
        }

        public void setJdbcUsername(String jdbcUsername) {
            this.jdbcUsername = jdbcUsername;
        }

        public String getJdbcPassword() {
            return jdbcPassword;
        }

        public void setJdbcPassword(String jdbcPassword) {
            this.jdbcPassword = jdbcPassword;
        }
    }


    public class SnowFlake {
        private Boolean enable = false;
        private String name = "leaf";
        private String zkAddress;
        private Integer zkPort;
        /**
         * workerId的初始值，如果有多机房部署，并且每个机房zk不共用时，需要设置每个机房workerId的区间段
         * 如：idc1 设置为：0~99，idc2设置为：100~199，idc3设置为：200~299 等等
         */
        private int initWorkerId = 0;

        public Boolean getEnable() {
            return enable;
        }

        public void setEnable(Boolean enable) {
            this.enable = enable;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getZkAddress() {
            return zkAddress;
        }

        public void setZkAddress(String zkAddress) {
            this.zkAddress = zkAddress;
        }

        public Integer getZkPort() {
            return zkPort;
        }

        public void setZkPort(Integer zkPort) {
            this.zkPort = zkPort;
        }

        public int getInitWorkerId() {
            return initWorkerId;
        }

        public void setInitWorkerId(int initWorkerId) {
            this.initWorkerId = initWorkerId;
        }
    }

}
