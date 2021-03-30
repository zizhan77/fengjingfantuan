package com.mem.vo.business.base.service;

import com.mem.vo.business.base.model.po.Visit;
import com.mem.vo.business.base.model.po.VisitComment;
import com.mem.vo.business.base.model.po.VisitCommentQuery;
import com.mem.vo.business.base.model.po.VisitQuery;
import com.mem.vo.business.base.model.vo.VisitCommentVO;
import com.mem.vo.business.base.model.vo.VisitReplyCommentVO;
import com.mem.vo.business.base.model.vo.VisitVO;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.PageBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VisitCommentService {
    /**
     * 添加接单中台表实体
     *
     * @param visitComment 回复接单中台表实体
     * @return 返回添加的接单中台表的ID
     */
    int insert(VisitComment visitComment);


    /**
     * 更新接单中台表
     *
     * @param visitComment 更新接单中台表实体
     * @return 返回更新的接单中台表的ID
     */
    int updateById(VisitComment visitComment);

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
    VisitComment findById(Long id);

    /**
     * 根据条件查询接单中台表
     *
     * @param query 查询条件
     * @return 返回查询的集合
     */
    List<VisitComment> findByCondition(@Param("condition") VisitCommentQuery query);

    /**
     * 根据条件查询接单中台表
     *
     * @param page 分页信息
     * @param query 查询条件
     */
    List<VisitComment> findByCondition(@Param("page") Page page, @Param("condition") VisitCommentQuery query);

    PageBean<VisitCommentVO> findAll(Integer pageNo, Integer pageSize, Long visitId);

    PageBean<VisitReplyCommentVO> findReplyAll(Integer pageNo, Integer pageSize, Long visitCommentId);
}
