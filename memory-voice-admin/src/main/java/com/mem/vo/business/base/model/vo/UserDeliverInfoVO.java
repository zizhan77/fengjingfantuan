package com.mem.vo.business.base.model.vo;

import com.mem.vo.business.base.model.po.Express;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.naming.Name;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-07-04 23:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDeliverInfoVO {
    private String name;
    private String phoneNumber;
    private String address;
    private String waybillCode;
    private Express express;
}
