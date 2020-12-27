package com.mem.vo.common.exception;

/**
 * 业务异常
 */
public class BizException extends RuntimeException {

    private Integer exceptionType;


    public BizException() {
        super();
        this.exceptionType = -1;
    }


    public BizException(String s) {
        super(s);
        this.exceptionType = -1;
    }


    public BizException(String message, Throwable cause) {
        super(message, cause);
        this.exceptionType = -1;
    }


    public BizException(Throwable cause) {
        super(cause);
        this.exceptionType = -1;
    }


    /**
     * @param exceptionType exceptionType is used for differing
     * explicit exceptionType in this Exception class
     */
    public BizException(String message, Integer exceptionType) {
        super(message);
        this.exceptionType = exceptionType;
    }

    /**
     * @param exceptionType exceptionType is used for differing
     * explicit exceptionType in this Exception class
     */
    public BizException(String message, Integer exceptionType, Throwable cause) {
        super(message, cause);
        this.exceptionType = exceptionType;
    }

    public Integer getExceptionType() {
        return exceptionType;
    }
}
