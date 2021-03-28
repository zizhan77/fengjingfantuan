package com.mem.vo.business.base.service.impl;

import com.mem.vo.business.base.model.po.Visit;
import com.mem.vo.business.base.model.po.VisitLike;
import com.mem.vo.business.base.model.po.VisitLikeQuery;
import com.mem.vo.business.base.model.vo.VisitLikeVO;
import com.mem.vo.business.base.service.VisitLikeService;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.PageBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author zhangsq
 * @date 2021-03-28 06:08
 * @desc 访谈用户点赞
 */
@Service
public class VisitLikeServiceImpl implements VisitLikeService {

    @Resource
    VisitLikeService visitLikeService;

    @Override
    public int insert(VisitLike visitLike) {
        Date date = new Date();
        visitLike.setCreateTime(date);
        visitLike.setUpdateTime(date);
        return visitLikeService.insert(visitLike);
    }

    @Override
    public int updateById(VisitLike visitLike) {
        Date date = new Date();
        visitLike.setUpdateTime(date);
        return visitLikeService.updateById(visitLike);
    }

    @Override
    public int deleteById(Long id) {
        return visitLikeService.deleteById(id);
    }

    @Override
    public Visit findById(Long id) {
        return visitLikeService.findById(id);
    }

    @Override
    public List<VisitLike> findByCondition(VisitLikeQuery query) {
        return visitLikeService.findByCondition(query);
    }

    @Override
    public List<VisitLike> findByCondition(Page page, VisitLikeQuery query) {
        return visitLikeService.findByCondition(page, query);
    }

    @Override
    public PageBean<VisitLikeVO> findAll(Integer pageNo, Integer pageSize) {
        return visitLikeService.findAll(pageNo, pageSize);
    }
}
