package com.wheel.demo.test.gateway;

import com.google.common.collect.Maps;
import com.wheel.common.util.JsonUtil;
import com.wheel.common.util.OkHttpUtil;
import org.junit.Test;

import java.util.Map;

/**
 * @desc 网关请求测试类
 * @author: zhouf
 */
public class TsGateway {

    @Test
    public void tsPost() throws Exception {
        String url = "http://127.0.0.1:9999/demo";
        Map<String, String> param = Maps.newHashMap();
        param.put("version", "2.0");
        param.put("method", "demo.ok");
        param.put("data", JsonUtil.toString(param));

        String apiResp = OkHttpUtil.postJsonSync(url, JsonUtil.toString(param));
        System.out.println(apiResp);
    }

    @Test
    public void tsRateLimit() throws Exception {
        String url = "http://127.0.0.1:9999/demo";
        Map<String, String> param = Maps.newHashMap();
        param.put("version", "1.0");
        param.put("method", "demo.ok");
        param.put("data", JsonUtil.toString(param));

        for (int i = 0; i < 50; i++) {
            String apiResp = OkHttpUtil.postJsonSync(url, JsonUtil.toString(param));
            System.out.println(apiResp);
        }
    }
}
