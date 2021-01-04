package com.mem.vo.business.base.model.po;

import lombok.Data;

import java.util.Date;

@Data
public class RewardBuilder {
    private Integer prizedId;

    private Integer id;

    private Integer prizeId;

    private Integer userId;

    private Date time;

    private Integer prizeType;

    private String prizeName;

    private String contactName;

    private String contactPhone;

    private String contactProvince;

    private String contactAddress;

    private Integer status;

    private Integer isDelete;

    private String drawCode;

    private String rewardDescription;

    private Integer activityId;

    private Integer integralNum;

    private Integer miniType;

    private ChangeCode changeCode;

    private CodeType codeTypeBean;

    private Integer keyId;
}
