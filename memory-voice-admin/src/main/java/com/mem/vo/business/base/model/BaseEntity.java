package com.mem.vo.business.base.model;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {

    private Long id;

    private Date createTime;

    private Date updateTime;

    private Integer isDelete;
}
