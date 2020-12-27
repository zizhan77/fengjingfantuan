package com.mem.vo.business.biz.model.vo.performance;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author litongwei
 * @description:
 * @date 2019/6/3 15:35
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FreightInfoVo {

    BasicFreightVo defaultFreight;

    List<BasicFreightVo> freightVoList;

    Integer total;


}
