package com.mem.vo.business.biz.model.vo.performance;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author litongwei
 * @description:
 * @date 2019/5/31 14:31
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasicArtistVo {

    /**
     *
     */
    private Long id;
    /**
     * 艺人名称
     */
    private String artistName;
    /**
     * 图片地址
     */
    private String thumbUrl;
    /**
     *
     */
    private String birthOneAddress;
    private String birthOneAddressName;

    /**
     *
     */
    private String birthTwoAdddress;
    private String birthTwoAddressName;

    /**
     *
     */
    private String basicInfo;

    /**
     * 创建时间
     */
    private Date createTime;
}
