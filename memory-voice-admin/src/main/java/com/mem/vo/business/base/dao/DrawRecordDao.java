package com.mem.vo.business.base.dao;

import java.util.List;

import com.mem.vo.business.base.model.po.DrawRecord;
import com.mem.vo.business.base.model.po.DrawRecordQuery;
import com.mem.vo.common.dto.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <br>
 * <b>功能：</b>DrawRecordDao<br>
 */
public interface DrawRecordDao {

    /**
     * 添加接单中台表实体
     *
     * @param drawRecord 回复接单中台表实体
     * @return 返回添加的接单中台表的ID
     */
    int insert(DrawRecord drawRecord);

    /**
     * 更新接单中台表
     *
     * @param drawRecord 更新接单中台表实体
     * @return 返回更新的接单中台表的ID
     */
    int updateById(DrawRecord drawRecord);

    /**
     * 删除接单中台表
     *
     * @param id 接单中台表ID
     */
    int deleteById(Long id);

    /**
     * 根据ID查询接单中台表
     *
     * @param id 接单中台表ID
     * @return 返回一条接单中台表
     */
    DrawRecord findById(Long id);


    /**
     * 根据条件查询接单中台表
     *
     * @param query 查询条件
     * @return 返回查询的集合
     */
    List<DrawRecord> findByCondition(@Param("condition") DrawRecordQuery query);

    /**
     * 根据条件查询接单中台表
     *
     * @param page 分页信息
     * @param query 查询条件
     */
    List<DrawRecord> findByCondition(@Param("page") Page page, @Param("condition") DrawRecordQuery query);

}
