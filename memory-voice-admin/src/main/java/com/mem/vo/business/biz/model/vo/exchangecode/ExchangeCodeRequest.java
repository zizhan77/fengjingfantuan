package com.mem.vo.business.biz.model.vo.exchangecode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by litongwei on 2019/6/29.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeCodeRequest {

    /**
     * 相关业务唯一标识key
     */
    private String businessKey;


    /**
     * 明细业务主键  list
     */
    private List<String> recordBusinessKeyList;

    /**
     * //业务标识
     */
    private Integer businessTag;

    /**
     * 备注
     */
    private String remark;

    /**
     * 生成数量
     */
    private Integer number;

    public static ExchangeCodeRequestBuilder builder() {
        return new ExchangeCodeRequestBuilder();
    }

    public static class ExchangeCodeRequestBuilder {
        private String businessKey;

        private List<String> recordBusinessKeyList;

        private Integer businessTag;

        private String remark;

        private Integer number;
    }

}
