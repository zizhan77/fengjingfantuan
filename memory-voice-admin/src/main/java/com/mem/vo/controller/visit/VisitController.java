package com.mem.vo.controller.visit;

import com.mem.vo.business.base.model.po.*;
import com.mem.vo.business.base.model.vo.VisitVO;
import com.mem.vo.business.base.service.VisitService;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.dto.PageBean;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.config.annotations.CommonExHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author zhangsq
 * @date 2021-03-28 2:55
 * @desc 专访相关操作
 */
@RestController
@RequestMapping("/visit")
@Slf4j
public class VisitController {

    @Resource
    private TokenService tokenService;

    @Resource
    private VisitService visitService;

    @CommonExHandler(key = "新增专访")
    @PostMapping("/insert")
    public ResponseDto<Integer> insert(@RequestHeader("token") String token, Visit visit) {
        //权限验证
        ResponseDto<Integer> responseDto = ResponseDto.successDto();

        try {
            CommonLoginContext context = tokenService.getContextByken(token);
            if (context.getUserId() == null) {
                responseDto.setCode(Integer.valueOf(3));
                return responseDto;
            }

            BizAssert.notNull(visit, BizCode.PARAM_NULL.getMessage());
            visit.setCreateUser(context.getUser().getId());
            return responseDto.successData(visitService.insert(visit));
        } catch (BizException e) {

            log.error("创建活动异常，参数:{},原因：{}", visit.getId(), e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("创建活动异常，参数:{}", visit.getId(), e);
            return responseDto.failSys();
        }
    }

    @CommonExHandler(key = "更新访谈信息")
    @PostMapping("/update")
    public ResponseDto<Integer> update(@RequestHeader("token") String token, Visit visit) {

        ResponseDto<Integer> responseDto = ResponseDto.successDto();

        try {
            CommonLoginContext context = tokenService.getContextByken(token);
            if (context.getUserId() == null) {
                responseDto.setCode(Integer.valueOf(3));
                return responseDto;
            }

            BizAssert.notNull(visit.getId(), BizCode.PARAM_NULL.getMessage());
            visit.setCreateUser(context.getUser().getId());
            Date date = new Date();
            visit.setUpdateTime(date);
            return responseDto.successData(visitService.updateById(visit));

        } catch (Exception e) {
            log.error("更新活动异常，参数:{}", visit.getId(), e);
            return responseDto.failSys();
        }
    }

    @CommonExHandler(key = "删除指定访谈")
    @PostMapping("/deleteById")
    public ResponseDto<Integer> deleteById(@RequestHeader("token") String token, String id) {
        //权限验证
        ResponseDto<Integer> responseDto = ResponseDto.successDto();

        try {
            CommonLoginContext context = tokenService.getContextByken(token);
            if (context.getUserId() == null) {
                responseDto.setCode(Integer.valueOf(3));
                return responseDto;
            }
            BizAssert.notNull(id, BizCode.PARAM_NULL.getMessage());
            return responseDto.successData(visitService.deleteById(Long.valueOf(id)));
        } catch (BizException e) {

            log.error("删除活动异常，参数:{},原因：{}", id, e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("删除活动异常，参数:{}", id, e);
            return responseDto.failSys();
        }
    }

    @PostMapping("/queryAll")
    public ResponseDto<PageBean<VisitVO>> queryAll(@RequestParam(required = true) Integer pageNo, @RequestParam(required = true) Integer pageSize) {
        //权限验证
        ResponseDto<PageBean<VisitVO>> responseDto = ResponseDto.successDto();
        try {
            PageBean<VisitVO> Page = visitService.findByPage(pageNo, pageSize);
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

    @PostMapping("/getVisit")
    public ResponseDto<PageBean<VisitVO>> getVisit(@RequestParam(required = true) Integer pageNo, @RequestParam(required = true) Integer pageSize, String name) {
        //权限验证
        ResponseDto<PageBean<VisitVO>> responseDto = ResponseDto.successDto();
        try {
            PageBean<VisitVO> Page = visitService.getVisit(pageNo, pageSize, name);
            return responseDto.successData(Page);
        } catch (Exception e) {
            responseDto.setCode(Integer.valueOf(1));
            return responseDto;
        }
    }
 }
