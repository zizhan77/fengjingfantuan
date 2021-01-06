package com.mem.vo.business.biz.model.vo.performance;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.mem.vo.business.base.model.po.PerformanceShow;
import com.mem.vo.business.base.model.po.Sponsor;
import com.mem.vo.business.base.model.po.TicketGear;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author litongwei
 * @description:
 * @date 2019/5/25 16:34
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceVo {



    /**
     * 主键 演出id
     */
    private Long id;
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

    private List<BasicArtistVo> artistVoList;

    /**
     * 表演场次
     */
    private String showIds;

    private List<PerformanceShow> showList;

    /**
     * 票档
     */
    private String ticketGearIds;

    private List<TicketGear> ticketGearList;
    /**
     *  票邮寄类型
     */
    private Integer ticketDeliverType;

    /**
     * 关键字
     */
    private String keyWords;

    /**
     * 关键字
     */
    private List<String> keyWordsList;
    /**
     * 演出时间
     */
    private Date showTime;

    /**
     * 演出时间
     */
    private String showTimeStr;
    /**
     * 演出图片 url
     */
    private String thumbUrl;
    /**
     * 场地 id
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
     * 演出状态名称
     */
    private String statusStr;

    /**
     * 起始价格
     */
    private BigDecimal lowPrice;

    /**
     * 开售时间
     */
    private Date startSaleDate;

    /**
     * 开售时间
     */
    private String startSaleDateStr;
    /**
     * 排序序号
     */
    private Integer sort;
    /**
     * 演出性质
     */
    private Integer performanceType;

    /**
     * 演出性质
     */
    private String performanceTypeStr;
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
     * 创建时间
     */
    private Date createTime;


    /**
     * 创建时间
     */
    private String createTimeStr;

    /**
     * 销售截止时间戳
     *
     */
    private Long saleEndTimeStamp;

    private String sponsorId;

    private List<Sponsor> sponsors;
}
