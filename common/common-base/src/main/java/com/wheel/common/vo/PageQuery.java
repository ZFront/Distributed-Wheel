package com.wheel.common.vo;

import java.io.Serializable;

/**
 * @description 分页查询参数
 * @author: zhouf
 * @date: 2020/5/29
 */
public class PageQuery implements Serializable {
    /**
     * 当前页数，默认为1，必须设置默认值，否则分页查询时容易报空指针异常
     */
    private int pageCurrent = 1;
    /**
     * 每页记录数，默认为20，必须设置默认值，否则分页查询时容易报空指针异常
     */
    private int pageSize = 20;
    /**
     * 是否需要总记录数，默认为true
     */
    private boolean isNeedTotalRecord = true;
    /**
     * 总记录数，根据查询条件查询出来的总记录数
     */
    private long totalRecord;
    /**
     * 排序字段及排序方向
     */
    private String sortColumns;

    public PageQuery() {
    }

    public int getPageCurrent() {
        return pageCurrent;
    }

    public void setPageCurrent(int pageCurrent) {
        this.pageCurrent = pageCurrent;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean getIsNeedTotalRecord() {
        return this.isNeedTotalRecord;
    }

    public void setIsNeedTotalRecord(boolean isNeedTotalRecord) {
        this.isNeedTotalRecord = isNeedTotalRecord;
    }

    public long getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(long totalRecord) {
        this.totalRecord = totalRecord;
    }

    public String getSortColumns() {
        return sortColumns;
    }

    public void setSortColumns(String sortColumns) {
        this.sortColumns = sortColumns;
    }

    public static PageQuery newInstance(int pageCurrent, int pageSize) {
        PageQuery pageParam = new PageQuery();
        pageParam.setPageCurrent(pageCurrent);
        pageParam.setPageSize(pageSize);
        return pageParam;
    }

    public static PageQuery newInstance(int pageCurrent, int pageSize, String sortColumns) {
        PageQuery pageParam = new PageQuery();
        pageParam.setPageCurrent(pageCurrent);
        pageParam.setPageSize(pageSize);
        pageParam.setSortColumns(sortColumns);
        return pageParam;
    }
}
