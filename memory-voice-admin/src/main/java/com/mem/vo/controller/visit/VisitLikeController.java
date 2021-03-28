package com.mem.vo.controller.visit;


import com.mem.vo.business.base.model.po.Visit;
import com.mem.vo.business.base.model.po.VisitLike;
import com.mem.vo.business.base.model.vo.VisitLikeVO;
import com.mem.vo.business.base.service.VisitLikeService;
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


/**
 * @author zhangsq
 * @date 2021-03-28 6:14
 * @desc 专访相关操作
 */
@RestController
@RequestMapping("/visit/like")
@Slf4j
public class VisitLikeController {
    @Resource
    private TokenService tokenService;

    @Resource
    private VisitLikeService visitLikeService;

    @Resource
    private VisitService visitService;

    @PostMapping("/insert")
    public ResponseDto<Integer> insert(@RequestHeader String token, Visit visit) {
        //权限验证
        ResponseDto<Integer> responseDto = ResponseDto.successDto();

        try {
            CommonLoginContext context = tokenService.getContextByken(token);
            if (context.getUserId() == null) {
                responseDto.setCode(Integer.valueOf(3));
                return responseDto;
            }

            BizAssert.notNull(visit, BizCode.PARAM_NULL.getMessage());
            visit.setLikes(visit.getLikes() + 1);
            visitService.updateById(visit);
            return responseDto.successData(visitService.insert(visit));
        } catch (BizException e) {

            log.error("创建活动异常，参数:{},原因：{}", visit.getId(), e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("创建活动异常，参数:{}", visit.getId(), e);
            return responseDto.failSys();
        }
    }

    @PostMapping("/update")
    public ResponseDto<Integer> update(@RequestHeader String token, VisitLike visitLike) {

        ResponseDto<Integer> responseDto = ResponseDto.successDto();

        try {
            CommonLoginContext context = tokenService.getContextByken(token);
            if (context.getUserId() == null) {
                responseDto.setCode(Integer.valueOf(3));
                return responseDto;
            }

            BizAssert.notNull(visitLike.getId(), BizCode.PARAM_NULL.getMessage());
            return responseDto.successData(visitLikeService.updateById(visitLike));

        } catch (Exception e) {
            log.error("更新活动异常，参数:{}", visitLike.getId(), e);
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
            return responseDto.successData(visitLikeService.deleteById(Long.valueOf(id)));
        } catch (BizException e) {

            log.error("删除活动异常，参数:{},原因：{}", id, e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("删除活动异常，参数:{}", id, e);
            return responseDto.failSys();
        }
    }

    @PostMapping("/queryAll")
    public ResponseDto<PageBean<VisitLikeVO>> queryAll(@RequestParam(required = true) Integer pageNo, @RequestParam(required = true) Integer pageSize) {
        //权限验证
        ResponseDto<PageBean<VisitLikeVO>> responseDto = ResponseDto.successDto();
        try {
            PageBean<VisitLikeVO> Page = visitLikeService.findAll(pageNo, pageSize);
            return responseDto.successData(Page);
        } catch (Exception e) {
            responseDto.setCode(Integer.valueOf(1));
            return responseDto;
        }
    }

    @PostMapping("/query")
    public ResponseDto<Visit> query(Long id) {
        //权限验证
        ResponseDto<Visit> responseDto = ResponseDto.successDto();
        try {
            Visit visit = visitService.findById(id);
            return responseDto.successData(visit);
        } catch (Exception e) {
            responseDto.setCode(Integer.valueOf(1));
            return responseDto;
        }
    }
}
