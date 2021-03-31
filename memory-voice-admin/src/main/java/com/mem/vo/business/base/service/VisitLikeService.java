package com.mem.vo.business.base.service;

import com.mem.vo.business.base.model.po.VisitLike;
import com.mem.vo.business.base.model.po.VisitLikeQuery;
import com.mem.vo.business.base.model.vo.VisitLikeVO;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.PageBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VisitLikeService {
    /**
     * 添加接单中台表实体
     *
     * @param visitLike 回复接单中台表实体
     * @return 返回添加的接单中台表的ID
     */
    int insert(VisitLike visitLike);


    /**
     * 更新接单中台表
     *
     * @param visitLike 更新接单中台表实体
     * @return 返回更新的接单中台表的ID
     */
    int updateById(VisitLike visitLike);

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
    VisitLike findById(Long id);

    /**
     * 根据条件查询接单中台表
     *
     * @param query 查询条件
     * @return 返回查询的集合
     */
    List<VisitLike> findByCondition(@Param("condition") VisitLikeQuery query);

    /**
     * 根据条件查询接单中台表
     *
     * @param page 分页信息
     * @param query 查询条件
     */
    List<VisitLike> findByCondition(@Param("page") Page page, @Param("condition") VisitLikeQuery query);

    PageBean<VisitLikeVO> findAll(Integer pageNo, Integer pageSize);


}
