package com.wheel.crawler.common;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @desc 爬虫代理
 * @author: zhouf
 */
public class CrawlerAgent {

    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.54 Safari/537.36";

    public static final String COOKIE = "";

    public static final String CONTENT_TYPE = "";

    public Map<String, String> getDefaultAgent() {
        Map<String, String> defaultAgentMap = Maps.newHashMap();
        defaultAgentMap.put("user-agent", USER_AGENT);
        return defaultAgentMap;
    }
}
