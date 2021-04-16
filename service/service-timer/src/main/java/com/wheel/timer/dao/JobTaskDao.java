package com.wheel.timer.dao;

import com.wheel.common.dao.MyBatisDao;
import com.wheel.timer.entity.JobTask;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @author: zhouf
 * @date: 2020/7/15
 */
@Repository
public class JobTaskDao extends MyBatisDao<JobTask, Long> {

    public JobTask getByJobName(String jobName) {
        Map<String, Object> param = new HashMap<>(1);
        param.put("jobName", jobName);
        return getOne(param);
    }
}
