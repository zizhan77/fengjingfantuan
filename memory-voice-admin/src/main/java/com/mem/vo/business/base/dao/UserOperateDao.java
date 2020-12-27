package com.mem.vo.business.base.dao;

import java.util.List;

import com.mem.vo.business.base.model.po.UserOperate;
import com.mem.vo.business.base.model.po.UserOperateQuery;
import com.mem.vo.common.dto.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <br>
 * <b>功能：</b>UserOperateDao<br>
 */
public interface UserOperateDao {

    /**
     * 添加接单中台表实体
     *
     * @param userOperate 回复接单中台表实体
     * @return 返回添加的接单中台表的ID
     */
    int insert(UserOperate userOperate);

    /**
     * 更新接单中台表
     *
     * @param userOperate 更新接单中台表实体
     * @return 返回更新的接单中台表的ID
     */
    int updateById(UserOperate userOperate);

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
    UserOperate findById(Long id);


    /**
     * 根据条件查询接单中台表
     *
     * @param query 查询条件
     * @return 返回查询的集合
     */
    List<UserOperate> findByCondition(@Param("condition") UserOperateQuery query);

    /**
     * 根据条件查询接单中台表
     *
     * @param page 分页信息
     * @param query 查询条件
     */
    List<UserOperate> findByCondition(@Param("page") Page page, @Param("condition") UserOperateQuery query);

}
