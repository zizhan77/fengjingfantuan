package com.mem.vo.common.dto;

import java.util.List;
public class PageBean<T> {
    private List<T> lists;

    private Integer pageSize = Integer.valueOf(10);

    private Integer totalPage;

    private Integer rows;

    private Integer start;

    private Integer pageNo = Integer.valueOf(1);

    private Integer wincount;

    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getWincount() {
        return this.wincount;
    }

    public void setWincount(Integer wincount) {
        this.wincount = wincount;
    }

    public Integer getStart() {
        return this.start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public List<T> getLists() {
        return this.lists;
    }

    public void setLists(List<T> lists) {
        this.lists = lists;
    }

    public Integer getPageNo() {
        return this.pageNo;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return this.totalPage;
    }

    public Integer getRows() {
        return this.rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
        this.totalPage = Integer.valueOf((rows.intValue() % this.pageSize.intValue() == 0) ? (rows.intValue() / this.pageSize.intValue()) : (rows.intValue() / this.pageSize.intValue() + 1));
    }

    public void setPageNo(Integer pageNo) {
        if (null == pageNo || pageNo.intValue() < 0) {
            this.pageNo = Integer.valueOf(1);
        } else if (pageNo.intValue() > this.totalPage.intValue() && this.totalPage.intValue() > 0) {
            this.pageNo = pageNo;
        } else {
            this.pageNo = pageNo;
        }
    }
}
