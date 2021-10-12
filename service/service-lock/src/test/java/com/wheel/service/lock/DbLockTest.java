package com.wheel.service.lock;

import com.wheel.service.lock.core.DbLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @description
 * @author: zhouf
 * @date: 2020/7/2
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceLockApp.class)
public class DbLockTest {

    @Autowired
    private DbLock dbLock;

    @Test
    public void testLock() {
        String resourceId = "testTryLock";
        int expireSecond = 3 * 60;

        String clientId = dbLock.tryLock(resourceId, expireSecond);
        System.out.println("tryLock clientId=" + clientId);
    }

    @Test
    public void testUnLock() {
        String resourceId = "testUnLock";
        int expireSecond = 3 * 60;

        String clientId = dbLock.tryLock(resourceId, expireSecond);
        System.out.println("tryLock clientId=" + clientId);

        boolean isSuccess = dbLock.unlock(resourceId, clientId);
        System.out.println("unlock isSuccess=" + isSuccess);
    }
}
