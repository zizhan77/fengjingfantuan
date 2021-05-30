package com.mem.vo.business.base.dao;
import com.mem.vo.business.base.model.po.Visit;
import com.mem.vo.business.base.model.po.VisitQuery;
import com.mem.vo.business.base.model.vo.VisitVO;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.PageBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VisitDao {

    /**
     * 添加接单中台表实体
     *
     * @param visit 回复接单中台表实体
     * @return 返回添加的接单中台表的ID
     */
    int insert(Visit visit);


    /**
     * 更新接单中台表
     *
     * @param visit 更新接单中台表实体
     * @return 返回更新的接单中台表的ID
     */
    int updateById(Visit visit);

    /**
     * 删除接单中台表
     *
     * @param id 接单中台表ID
     */
    int deleteById(Long id);


    /**
     * 启用接单中台表
     *
     * @param id 接单中台表ID
     */
    int showById(Long id);

    /**
     * 根据ID查询接单中台表
     *
     * @param id 接单中台表ID
     * @return 返回一条接单中台表
     */
    Visit findById(Long id);

    /**
     * 根据条件查询接单中台表
     *
     * @param query 查询条件
     * @return 返回查询的集合
     */
    List<Visit> findByCondition(@Param("condition") VisitQuery query);

    /**
     * 根据条件查询接单中台表
     *
     * @param page 分页信息
     * @param query 查询条件
     */
    List<Visit> findByCondition(@Param("page") Page page, @Param("condition") VisitQuery query);

    PageBean<VisitVO> findAll(Integer pageNo, Integer pageSize);

    List<VisitVO> findByPage(@Param("paging") PageBean<VisitVO> paging);

    List<VisitVO> getVisit(@Param("paging") PageBean<VisitVO> paging, String name);

    int findByPageCount();
}
