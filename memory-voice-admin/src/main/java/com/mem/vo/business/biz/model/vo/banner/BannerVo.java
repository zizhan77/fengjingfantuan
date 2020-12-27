package com.mem.vo.business.biz.model.vo.banner;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author litongwei
 * @description:
 * @date 2019/6/1 15:22
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BannerVo {

    private Long id;
    /**
     * 开屏页名称
     */
    private String bannerName;
    /**
     * 排序序号
     */
    private Integer sort;
    /**
     * 平台
     */
    private String platform;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 连接地址
     */
    private String url;
    /**
     *
     */
    private String oneAddress;

    private String oneAddressName;

    /**
     *
     */
    private String twoAddress;

    private String twoAddressName;
    /**
     * 是否全国 1 是  0 否
     */
    private Integer allPlace;
    /**
     * 图片地址
     */
    private String thumbUrl;
    /**
     * 是否启用
     */
    private Integer enable;

    /**
     * 创建时间
     */
    private Date createTime;


}
