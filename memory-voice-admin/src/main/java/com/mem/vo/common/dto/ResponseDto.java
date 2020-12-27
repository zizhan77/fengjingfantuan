package com.mem.vo.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by litongwei on 2019/5/19.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto<T> {

    private Integer code = SUCCESS;

    private String message = SUCCESS_MSG;

    private T data;

    public static final Integer SUCCESS = 0;

    public static final String SUCCESS_MSG = "成功";

    public static final Integer FAIL = 1;

    public static final String SYS_FAIL_MSG = "系统异常";

    public static final Integer BUSINESS_FAIL = 2;

    public static final String BUSINESS_FAIL_MSG = "业务异常";

    public static final Integer LOGIN_TIMEOUT = 3;

    public static final String LOGIN_TIMEOUT_MSG = "超时登录异常";

    public static final Integer NOT_AUTHORIZE = 5;

    public static final String NOT_AUTHORIZE_MSG = "未授权异常，需要绑定手机号";

    public static ResponseDto successDto() {
        return new ResponseDto(SUCCESS, SUCCESS_MSG, null);
    }


    public ResponseDto successData(T data) {
        this.code = SUCCESS;
        this.message = SUCCESS_MSG;
        this.data = data;
        return this;
    }


    public ResponseDto failData(String message) {
        this.code = FAIL;
        this.message = message;
        return this;
    }

    public ResponseDto failSys() {
        this.code = FAIL;
        this.message = SYS_FAIL_MSG;
        return this;
    }
    public ResponseDto businessFail(String message) {
        this.code = BUSINESS_FAIL;
        this.message = message;
        return this;
    }

    public ResponseDto loginTimeout() {
        this.code = LOGIN_TIMEOUT;
        this.message = LOGIN_TIMEOUT_MSG;
        return this;
    }


    public ResponseDto loginNotAuthorize() {
        this.code = NOT_AUTHORIZE;
        this.message = NOT_AUTHORIZE_MSG;
        return this;
    }

}
