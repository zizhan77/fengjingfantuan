package com.mem.vo.business.biz.model.vo.basic;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author litongwei
 * @description:
 * @date 2019/6/4 15:54
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasicAddressNode {

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
     * 叶子节点
     */
    private List<BasicAddressNode> children;

}
