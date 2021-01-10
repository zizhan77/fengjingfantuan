package com.mem.vo.common.dto;

import java.util.List;

public class PageBeanCopy<T> {
    private List<T> lists;

    public void setLists(List<T> lists) {
        this.lists = lists;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public void setWincount(Integer wincount) {
        this.wincount = wincount;
    }

    public List<T> getLists() {
        return this.lists;
    }

    private Integer pageSize = Integer.valueOf(10);

    private Integer totalPage;

    private Integer rows;

    private Integer start;

    public Integer getPageSize() {
        return this.pageSize;
    }

    public Integer getTotalPage() {
        return this.totalPage;
    }

    public Integer getRows() {
        return this.rows;
    }

    public Integer getStart() {
        return this.start;
    }

    private Integer pageNo = Integer.valueOf(1);

    private Integer wincount;

    public Integer getPageNo() {
        return this.pageNo;
    }

    public Integer getWincount() {
        return this.wincount;
    }
}
