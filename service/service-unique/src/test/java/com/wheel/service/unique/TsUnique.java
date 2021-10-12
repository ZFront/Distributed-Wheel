package com.wheel.service.unique;

import com.wheel.service.unique.biz.UuidBiz;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @description
 * @author: zhouf
 * @date: 2020/8/7
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceUniqueApp.class)
public class TsUnique {

    @Autowired
    UuidBiz uuidBiz;

    @Test
    public void tsId() {
        System.out.println(uuidBiz.getId());
    }
}
