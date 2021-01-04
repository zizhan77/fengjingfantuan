package com.mem.vo.business.base.model.po;

import lombok.Data;

import java.util.Date;

@Data
public class UserAddressBuilder {
    private Long id;

    private Long userId;

    private String receiverName;

    private String receiverMobile;

    private String destOneAddress;

    private String destTwoAddress;

    private String destThreeAddress;

    private String destFourAddress;

    private String address;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private Integer isDelete;

    private Date ts;

    private Integer isDefault;
}
