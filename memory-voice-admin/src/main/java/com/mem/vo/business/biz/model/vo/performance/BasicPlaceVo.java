package com.mem.vo.business.biz.model.vo.performance;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.mem.vo.business.base.model.vo.PlaceArtistVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author litongwei
 * @description:
 * @date 2019/5/26 18:30
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasicPlaceVo {

    /**
     * 主键
     */
    private Long id;
    /**
     * 场地名称
     */
    private String name;
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
     *
     */
    private String threeAddress;

    private String threeAddressName;

    /**
     * 详细地址
     */
    private String address;
    /**
     * 是否选座 1 支持 0 不支持
     */
    private Integer chooseSeat;
    /**
     *是否启用 1 启用 0 不启用
     */
    private Integer enable;
    /**
     * 经度
     */
    private BigDecimal longitude;
    /**
     *纬度
     */
    private BigDecimal latitude;

    private String phone;

    /**
     * 创建时间
     */
    private Date createTime;

    private String topcount;

    private String lowcount;


    private String introduce;
    private BigDecimal lowprice;

    private BigDecimal topprice;


    private Integer artistId;

    private String artistNmae;

    private List<PlaceArtistVO> artistList;
}
