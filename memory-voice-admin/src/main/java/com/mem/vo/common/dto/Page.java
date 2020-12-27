package com.mem.vo.common.dto;

import java.util.List;

public class Page<T> {


    private List<T> data;

    private Integer total;
    private Integer limit = 10;
    private Integer page;
    private String orderByField;
    private String orderByType;

    public Page() {
    }


    public List<T> getData() {
        return this.data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }


    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getLimit() {
        return this.limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPage() {
        return this.page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getOrderByField() {
        return this.orderByField;
    }

    public void setOrderByField(String orderByField) {
        this.orderByField = orderByField;
    }

    public String getOrderByType() {
        return this.orderByType;
    }

    public void setOrderByType(String orderByType) {
        this.orderByType = orderByType;
    }
}

