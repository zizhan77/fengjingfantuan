package com.mem.vo.business.biz.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author litongwei
 * @description: key -value 格式基础数据通用实体
 * @date 2019/5/26 17:32
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasicCommonVo {

    //编码
    private String code;


    //名称
    private String name;

}
