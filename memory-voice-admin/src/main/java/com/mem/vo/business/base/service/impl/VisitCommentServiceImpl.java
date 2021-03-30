package com.mem.vo.business.base.service.impl;

import com.mem.vo.business.base.dao.VisitCommentDao;
import com.mem.vo.business.base.model.po.VisitComment;
import com.mem.vo.business.base.model.po.VisitCommentQuery;
import com.mem.vo.business.base.model.vo.VisitCommentVO;
import com.mem.vo.business.base.model.vo.VisitReplyCommentVO;
import com.mem.vo.business.base.service.VisitCommentService;
import com.mem.vo.common.constant.VisitCommentStatus;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.PageBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author zhangsq
 * @date 2021-03-28 5:56
 */
@Service
public class VisitCommentServiceImpl implements VisitCommentService {

    private final static Logger log = LogManager.getLogger(VisitCommentServiceImpl.class);

    @Resource
    private VisitCommentDao visitCommentDao;

    @Override
    public int insert(VisitComment visitComment) {

        Date date = new Date();
        visitComment.setCreateTime(date);
        visitComment.setUpdateTime(date);
        visitComment.setStatus(VisitCommentStatus.COMMENTPASS.getCode());
        return visitCommentDao.insert(visitComment);
    }

    @Override
    public int updateById(VisitComment visitComment) {
        Date date = new Date();
        visitComment.setUpdateTime(date);
        visitComment.setStatus(VisitCommentStatus.COMMENTPASS.getCode());
        return visitCommentDao.updateById(visitComment);
    }

    @Override
    public int deleteById(Long id) {
        return visitCommentDao.deleteById(id);
    }

    @Override
    public VisitComment findById(Long id) {
        return visitCommentDao.findById(id);
    }

    @Override
    public List<VisitComment> findByCondition(VisitCommentQuery query) {
        return visitCommentDao.findByCondition(query);
    }

    @Override
    public List<VisitComment> findByCondition(Page page, VisitCommentQuery query) {
        return visitCommentDao.findByCondition(page, query);
    }

    @Override
    public PageBean<VisitCommentVO> findAll(Integer pageNo, Integer pageSize,Long visitId) {
        PageBean<VisitCommentVO> pager = new PageBean<>();
        pager.setPageSize(pageSize);
        pager.setPageNo(pageNo);
        int first = (pager.getPageNo().intValue() - 1) * pager.getPageSize().intValue();
        pager.setStart(Integer.valueOf(first));
        List<VisitCommentVO> list = visitCommentDao.findByPage(pager, visitId);
        for (VisitCommentVO visitCommentVO: list) {
            PageBean<VisitReplyCommentVO> pape = findReplyAll(1, 2, visitCommentVO.getVisitId());
            visitCommentVO.setVisitReplyCommentVOs(pape.getLists());
        }
        pager.setLists(list);
        return pager;
    }

    @Override
    public PageBean<VisitReplyCommentVO> findReplyAll(Integer pageNo, Integer pageSize, Long visitCommentId) {
        PageBean<VisitReplyCommentVO> pager = new PageBean<>();
        pager.setPageSize(pageSize);
        pager.setPageNo(pageNo);
        int first = (pager.getPageNo().intValue() - 1) * pager.getPageSize().intValue();
        pager.setStart(Integer.valueOf(first));
        List<VisitReplyCommentVO> list = visitCommentDao.findReplyByPage(pager, visitCommentId);
        pager.setLists(list);
        return pager;
    }
}
