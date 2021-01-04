package com.mem.vo.business.base.model.po;

public class Criterion {
    private String condition;

    private Object value;

    private Object secondValue;

    private boolean noValue;

    private boolean singleValue;

    private boolean betweenValue;

    private boolean listValue;

    private String typeHandler;

    public String getCondition() {
        return this.condition;
    }

    public Object getValue() {
        return this.value;
    }

    public Object getSecondValue() {
        return this.secondValue;
    }

    public boolean isNoValue() {
        return this.noValue;
    }

    public boolean isSingleValue() {
        return this.singleValue;
    }

    public boolean isBetweenValue() {
        return this.betweenValue;
    }

    public boolean isListValue() {
        return this.listValue;
    }

    public String getTypeHandler() {
        return this.typeHandler;
    }

    protected Criterion(String condition) {
        this.condition = condition;
        this.typeHandler = null;
        this.noValue = true;
    }

    protected Criterion(String condition, Object value, String typeHandler) {
        this.condition = condition;
        this.value = value;
        this.typeHandler = typeHandler;
        if (value instanceof java.util.List) {
            this.listValue = true;
        } else {
            this.singleValue = true;
        }
    }

    protected Criterion(String condition, Object value) {
        this(condition, value, (String)null);
    }

    protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
        this.condition = condition;
        this.value = value;
        this.secondValue = secondValue;
        this.typeHandler = typeHandler;
        this.betweenValue = true;
    }

    protected Criterion(String condition, Object value, Object secondValue) {
        this(condition, value, secondValue, null);
    }
}
