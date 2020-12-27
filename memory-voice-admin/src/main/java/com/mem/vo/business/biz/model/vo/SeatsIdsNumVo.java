package com.mem.vo.business.biz.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author litongwei
 * @description: key -value 格式基础数据通用实体
 * @date 2019/5/26 17:32
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeatsIdsNumVo implements Serializable {

    //编码
    private String id;

    //区域Id
    private String areaId;


/*    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SeatsIdsNumVo)) return false;
        SeatsIdsNumVo vo = (SeatsIdsNumVo) o;
        return getAreaId().equals(vo.getAreaId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAreaId());
    }*/
}
