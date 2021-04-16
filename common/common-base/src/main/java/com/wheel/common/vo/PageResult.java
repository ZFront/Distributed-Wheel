package com.wheel.common.vo;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * @Description:分页查询结果返回的bean
 * @author: zhouf
 * @Date: 2019/2/3
 */
public class PageResult<T> {
    /**
     * 当前页
     */
    private Integer pageCurrent;

    /**
     * 每页数量
     */
    private Integer pageSize;

    /**
     * 总记录数
     */
    private Long totalRecord;

    private T data;

    public Integer getPageCurrent() {
        return pageCurrent;
    }

    public void setPageCurrent(Integer pageCurrent) {
        this.pageCurrent = pageCurrent;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Long totalRecord) {
        this.totalRecord = totalRecord;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> PageResult<T> newInstance(PageQuery pageParam, T data) {
        return PageResult.newInstance(data, pageParam.getPageCurrent(), pageParam.getPageSize(), (long) calcDataLength(data));
    }

    public static <T> PageResult<T> newInstance(T data, Integer pageCurrent, Integer pageSize) {
        return PageResult.newInstance(data, pageCurrent, pageSize, (long) calcDataLength(data));
    }

    public static <T> PageResult<T> newInstance(T data, PageQuery pageParam, Long totalRecord) {
        return PageResult.newInstance(data, pageParam.getPageCurrent(), pageParam.getPageSize(), totalRecord);
    }

    public static <T> PageResult<T> newInstance(T data, Integer pageCurrent, Integer pageSize, Long totalRecord) {
        PageResult<T> pageResult = new PageResult<T>();
        pageResult.setPageCurrent(pageCurrent);
        pageResult.setPageSize(pageSize);
        pageResult.setData(data);
        pageResult.setTotalRecord(totalRecord);
        return pageResult;
    }

    /**
     * 计算当前 data 的长度
     *
     * @return
     */
    public int length() {
        return calcDataLength(this.data).intValue();
    }

    /**
     * 计算 data 的长度
     *
     * @param data
     */
    public static <T> Integer calcDataLength(T data) {
        if (data == null) {
            return 0;
        } else if (data instanceof Collection) {
            return ((Collection) data).size();
        } else if (data instanceof Map) {
            return ((Map) data).size();
        } else if (data.getClass().isArray()) {
            return Array.getLength(data);
        } else {//其他的 String、Integer、Date、Object等对象则统一计算为1，表示一个对象
            return 1;
        }
    }
}
