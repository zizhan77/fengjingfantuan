package com.mem.vo.business.base.model.vo;

import com.mem.vo.business.base.model.po.BasicPlace;
import com.mem.vo.business.base.model.po.PerformanceShow;
import lombok.Data;

import java.util.Date;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-07-03 21:39
 */
@Data
public class UserLikeVO {
    /**
     *
     */
    private Integer id;
    /**
     * 演出
     */
    private Integer performanceId;
    /**
     *
     */
    private Integer userId;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private String createUser;
    /**
     *
     */
    private Date updateTime;
    /**
     *
     */
    private String updateUser;

    /**
     * 主键 演出id
     */
    private Long pId;
    /**
     * 演出名称
     */
    private String title;
    /**
     * 简短名称
     */
    private String simpleTitle;
    /**
     * 演出推荐语
     */
    private String description;
    /**
     * 艺人列表
     */
    private String artistIds;
    /**
     * 票档
     */
    private String ticketGearIds;
    /**
     *  票邮寄类型
     */
    private Integer ticketDeliverType;
    /**
     * 关键字
     */
    private String keyWords;
    /**
     * 演出时间
     */
    private Date showTime;
    /**
     * 演出图片 url
     */
    private String thumbUrl;
    /**
     *
     */
    private Long placeId;
    /**
     * 演出状态
     */
    private Integer status;

    /**
     * 启用停用
     */
    private Integer enable;

    /**
     * 开售时间
     */
    private Date startSaleDate;
    /**
     * 排序序号
     */
    private Integer sort;
    /**
     * 演出分类
     */
    private Integer performanceType;
    /**
     * 演出详情
     */
    private String detail;
    /**
     * 购买须知
     */
    private String buyNotes;
    /**
     * 演出性质
     */
    private Integer performanceNature;
    /**
     * 限购张数
     */
    private Integer limitNumber;

    /**
     * 场次id
     */
    private Long showId;

    /**
     * 演出信息
     */
    private PerformanceShow performanceShow;
    private BasicPlace basicPlace;
    /**
     *销售截止时间
     */
    private Date saleEndTimeStamp;
}
