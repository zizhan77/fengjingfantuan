package com.mem.vo.business.base.dao;

import java.util.List;

import com.mem.vo.business.base.model.po.AuthorityRole;
import com.mem.vo.business.base.model.po.AuthorityRoleQuery;
import com.mem.vo.common.dto.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <br>
 * <b>功能：</b>AuthorityRoleDao<br>
 */
public interface AuthorityRoleDao {

    /**
     * 添加接单中台表实体
     *
     * @param authorityRole 回复接单中台表实体
     * @return 返回添加的接单中台表的ID
     */
    int insert(AuthorityRole authorityRole);

    /**
     * 更新接单中台表
     *
     * @param authorityRole 更新接单中台表实体
     * @return 返回更新的接单中台表的ID
     */
    int updateById(AuthorityRole authorityRole);

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
    AuthorityRole findById(Long id);


    /**
     * 根据条件查询接单中台表
     *
     * @param query 查询条件
     * @return 返回查询的集合
     */
    List<AuthorityRole> findByCondition(@Param("condition") AuthorityRoleQuery query);

    /**
     * 根据条件查询接单中台表
     *
     * @param page 分页信息
     * @param query 查询条件
     */
    List<AuthorityRole> findByCondition(@Param("page") Page page, @Param("condition") AuthorityRoleQuery query);

    int updateByRoleIds(@Param("list")List<Integer> roleIds, @Param("updateUser") String updateUser);

}
