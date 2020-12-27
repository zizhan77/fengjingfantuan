package com.mem.vo.business.base.model.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-07-02 21:34
 */
@Data
@Builder
public class DiceVO {
    /**
     * 活动ID
     */
    private Integer id;
    /**
     * 活动名称
     */
    private String name;
}
