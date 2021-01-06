package com.mem.vo.business.biz.model.vo.basic;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author litongwei
 * @description:
 * @date 2019/6/3 17:01
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasicAddressVo {


    /**
     *
     */
    private Long id;

    /**
     *
     */
    private String addressCode;
    /**
     *
     */
    private String addressName;
    /**
     *
     */
    private String parentCode;
    /**
     * 1省份；2城市；3区县；4街道
     */
    private Integer level;
    /**
     *
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    public static class BasicAddressVoBuilder {
        private Long id;

        private String addressCode;

        private String addressName;

        private String parentCode;

        private Integer level;

        private Integer status;

        private Date createTime;

    }

    public static BasicAddressVoBuilder builder() {
        return new BasicAddressVoBuilder();
    }

}
