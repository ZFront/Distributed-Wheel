package com.wheel.demo.test.mq.vo;

import java.io.Serializable;
import java.util.TreeMap;

/**
 * @desc
 * @author: zhouf
 */
public class RetryNotifyVo implements Serializable {

    /**
     * 当前自动补单次数 -- auto
     */
    private int currentRetryTimes;

    /**
     * 总重试次数 -- auto
     */
    private int maxRetryTimes;

    /**
     * M
     * <p>
     * reissueRules自动按照ASSIC码排序<br/>
     * key :补单次数，只能递增 value：延迟补单时间,单位为秒<br/>
     * 如第一次延迟30s自动补单,第二次延迟35s：{1:30,2:35} <br/>
     * example:
     * reissueRules.put(1,30)
     * reissueRules.put(2,35)
     * <p>
     * example 2:
     * reissueRules.put(35,30)
     * reissueRules.put(36,35)
     * reissueRules.put(37,35)
     */
    private TreeMap<Integer, Integer> retryRules;

    /**
     * 流水号
     */
    private String trxNo;

    public RetryNotifyVo(TreeMap<Integer, Integer> retryRules) {
        this.retryRules = retryRules;
        this.maxRetryTimes = retryRules.size();
        this.currentRetryTimes = retryRules.firstKey();
    }

    /**
     * 获取下一次补单延迟时间，并更新补单次数为下一次，
     * 没有下次  返回-1，不更新补单次数
     * 有下一次 返回延迟时间，更新补单次数
     *
     * @return
     */
    public Integer nextDelayTime() {
        //获取下一个延迟时间的次数
        Integer nextDelayTimes = retryRules.higherKey(currentRetryTimes);
        if (nextDelayTimes == null) {
            return -1;
        }
        currentRetryTimes = nextDelayTimes;
        return retryRules.get(nextDelayTimes);
    }

    /**
     * 获取上一次补单延迟时间，并更新补单次数为上一次，
     * 没有上次  返回-1，不更新补单次数
     * 有上次   返回延迟时间，更新补单次数为上次
     *
     * @return
     */
    public Integer prevDelayTime() {
        //获取下一个延迟时间的次数
        Integer prevDelayTimes = retryRules.lowerKey(currentRetryTimes);
        if (prevDelayTimes == null) {
            return -1;
        }
        currentRetryTimes = prevDelayTimes;
        return retryRules.get(prevDelayTimes);
    }

    /**
     * 获取当前补单延迟时间
     *
     * @return
     */
    public Integer currentRetryTime() {
        return retryRules.get(currentRetryTimes);
    }

    /**
     * 获取当前补单次数
     *
     * @return
     */
    public int getCurrentRetryTimes() {
        return currentRetryTimes;
    }

    public TreeMap<Integer, Integer> getRetryRules() {
        return retryRules;
    }

    public int getMaxRetryTimes() {
        return maxRetryTimes;
    }

    public String getTrxNo() {
        return trxNo;
    }

    public void setTrxNo(String trxNo) {
        this.trxNo = trxNo;
    }
}
