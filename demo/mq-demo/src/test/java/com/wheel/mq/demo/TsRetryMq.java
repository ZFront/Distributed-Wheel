package com.wheel.mq.demo;

import com.wheel.mq.demo.biz.NotityDemoBiz;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @desc
 * @author: zhouf
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MqDemoApplication.class)
public class TsRetryMq {

    @Autowired
    private NotityDemoBiz notityDemoBiz;

    @Test
    public void testRetryMq() {
        notityDemoBiz.sendRetryNotify("ceshi");
    }

}
