package com.wheel.web.api.admin;

import com.wheel.common.util.JsonUtil;
import com.wheel.web.api.common.config.WebApiProp;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @desc
 * @author: zhouf
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebAdminApplication.class)
public class WebApiTest {

    @Autowired
    private WebApiProp webApiProp;

    @Test
    public void testProp() {
        log.info(JsonUtil.toString(webApiProp));
    }
}
