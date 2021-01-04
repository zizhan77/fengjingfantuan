package com.mem.vo.business.base.model.po;

import com.mem.vo.business.base.model.vo.EticketVO;
import com.mem.vo.business.base.model.vo.IntegralVO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class PrizeDBuilder {
    private Integer id;

    private Integer prizeId;

    private Integer prizeType;

    private Integer integralQty;

    private BigDecimal prob;

    private String eticketCode;

    private String name;

    private Integer isChange;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private Integer isDelete;

    private Date ts;

    private Long activityId;

    private String prizeIntro;

    private String prizeName;

    private Integer prizeNum;

    private List<IntegralVO> integralProbList;

    private List<EticketVO> eticketList;

    private Integer status;

    private Integer level;

    private String count;

    private BigDecimal integralProb;

    private Integer prizedCount;

    private Integer ticketUnit;

    private Integer miniType;

    private Integer codeType;

    private Integer keyId;

    private ChangeCode changeCode;

    private CodeType codeTypeBean;

    private Integer sendNum;
}
