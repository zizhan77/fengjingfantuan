/*
 * File : Dialect.java
 * date : Sep 18, 2012 9:48:34 AM
 */
package com.mem.vo.common.interceptor;

import java.util.LinkedHashMap;


public abstract class Dialect {

    public abstract String getLimitString(String sql, int skipResults, int maxResults);

    public abstract String getCountString(String sql);

    public abstract String getOrderString(String sql, LinkedHashMap<String, String> taskItems);

    public enum Type {
        MYSQL, ORACLE
    }

}
