package com.mem.vo.controller.visit;

import com.mem.vo.business.base.model.po.Visit;
import com.mem.vo.business.base.model.po.VisitComment;
import com.mem.vo.business.base.model.vo.VisitCommentVO;
import com.mem.vo.business.base.model.vo.VisitReplyCommentVO;
import com.mem.vo.business.base.service.VisitCommentService;
import com.mem.vo.business.base.service.VisitService;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.dto.PageBean;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author zhangsq
 * @date 2021-03-28 5:59
 * @desc 专访评论相关操作
 */
@RestController
@RequestMapping("/visit/comment")
@Slf4j
public class VisitCommentController {
    @Resource
    private TokenService tokenService;

    @Resource
    private VisitCommentService visitCommentService;

    @Resource
    private VisitService visitService;

    @PostMapping("/insert")
    public ResponseDto<Integer> insert(@RequestHeader String token, VisitComment visitComment) {
        //权限验证
        ResponseDto<Integer> responseDto = ResponseDto.successDto();

        try {
            CommonLoginContext context = tokenService.getContextByken(token);
            if (context.getUserId() == null) {
                responseDto.setCode(Integer.valueOf(3));
                return responseDto;
            }

            BizAssert.notNull(visitComment, BizCode.PARAM_NULL.getMessage());
            visitComment.setCreateUser(context.getUser().getId());
            Visit visit = visitService.findById(visitComment.getVisitId());
            visit.setComments(visit.getComments() + 1);
            visitService.updateById(visit);
            return responseDto.successData(visitCommentService.insert(visitComment));
        } catch (BizException e) {

            log.error("创建活动异常，参数:{},原因：{}", visitComment.getId(), e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("创建活动异常，参数:{}", visitComment.getId(), e);
            return responseDto.failSys();
        }
    }

    @PostMapping("/update")
    public ResponseDto<Integer> update(@RequestHeader String token, VisitComment visitComment) {

        ResponseDto<Integer> responseDto = ResponseDto.successDto();

        try {
            CommonLoginContext context = tokenService.getContextByken(token);
            if (context.getUserId() == null) {
                responseDto.setCode(Integer.valueOf(3));
                return responseDto;
            }

            BizAssert.notNull(visitComment.getId(), BizCode.PARAM_NULL.getMessage());
            return responseDto.successData(visitCommentService.updateById(visitComment));

        } catch (Exception e) {
            log.error("更新活动异常，参数:{}", visitComment.getId(), e);
            return responseDto.failSys();
        }
    }

    @PostMapping("/deleteById")
    public ResponseDto<Integer> deleteById(@RequestHeader("token") String token, Long id) {
        //权限验证
        ResponseDto<Integer> responseDto = ResponseDto.successDto();

        try {
            CommonLoginContext context = tokenService.getContextByken(token);
            if (context.getUserId() == null) {
                responseDto.setCode(Integer.valueOf(3));
                return responseDto;
            }
            BizAssert.notNull(id, BizCode.PARAM_NULL.getMessage());
            VisitComment visitComment = visitCommentService.findById(id);
            Visit visit = visitService.findById(visitComment.getVisitId());
            visit.setComments(visit.getComments() - 1);
            visitService.updateById(visit);
            return responseDto.successData(visitCommentService.deleteById(Long.valueOf(id)));
        } catch (BizException e) {

            log.error("删除活动异常，参数:{},原因：{}", id, e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("删除活动异常，参数:{}", id, e);
            return responseDto.failSys();
        }
    }

    @PostMapping("/queryAll")
    public ResponseDto<PageBean<VisitCommentVO>> queryAll(@RequestParam(required = true) Integer pageNo, @RequestParam(required = true) Integer pageSize, @RequestParam(required = true) Long visitId ) {
        //权限验证
        ResponseDto<PageBean<VisitCommentVO>> responseDto = ResponseDto.successDto();
        try {
            PageBean<VisitCommentVO> Page = visitCommentService.findAll(pageNo, pageSize, visitId);
            return responseDto.successData(Page);
        } catch (Exception e) {
            responseDto.setCode(Integer.valueOf(1));
            return responseDto;
        }
    }

    @PostMapping("/queryReplayAll")
    public ResponseDto<PageBean<VisitReplyCommentVO>> queryReplayAll(@RequestParam(required = true) Integer pageNo, @RequestParam(required = true) Integer pageSize, @RequestParam(required = true) Long visitCommentId) {
        //权限验证
        ResponseDto<PageBean<VisitReplyCommentVO>> responseDto = ResponseDto.successDto();
        try {
            PageBean<VisitReplyCommentVO> Page = visitCommentService.findReplyAll(pageNo, pageSize, visitCommentId);
            return responseDto.successData(Page);
        } catch (Exception e) {
            responseDto.setCode(Integer.valueOf(1));
            return responseDto;
        }
    }

    @PostMapping("/query")
    public ResponseDto<VisitComment> query(Long id) {
        //权限验证
        ResponseDto<VisitComment> responseDto = ResponseDto.successDto();
        try {
            VisitComment visitComment = visitCommentService.findById(id);
            return responseDto.successData(visitComment);
        } catch (Exception e) {
            responseDto.setCode(Integer.valueOf(1));
            return responseDto;
        }
    }

    @PostMapping("/likes")
    public ResponseDto<Integer> like(@RequestHeader String token,@RequestParam(required = true) long id,@RequestParam(required = true) int likes) {
        //权限验证
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
       VisitComment visitComment = visitCommentService.findById(id);

        try {
            CommonLoginContext context = tokenService.getContextByken(token);
            if (context.getUserId() == null) {
                responseDto.setCode(Integer.valueOf(3));
                return responseDto;
            }

            BizAssert.notNull(visitComment, BizCode.PARAM_NULL.getMessage());
            visitComment.setLikes(visitComment.getLikes() + likes);
            return responseDto.successData(visitCommentService.updateById(visitComment));
        } catch (BizException e) {

            log.error("创建活动异常，参数:{},原因：{}", visitComment.getId(), e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("创建活动异常，参数:{}", visitComment.getId(), e);
            return responseDto.failSys();
        }
    }
}
