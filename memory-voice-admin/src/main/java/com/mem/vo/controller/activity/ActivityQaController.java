package com.mem.vo.controller.activity;

import com.alibaba.fastjson.JSONArray;
import com.mem.vo.business.base.model.po.Activity;
import com.mem.vo.business.base.model.po.ActivityQa;
import com.mem.vo.business.base.model.po.ActivityQaQuery;
import com.mem.vo.business.base.service.ActivityQaService;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.constant.SourceType;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-06-22 21:57
 */
@RestController
@RequestMapping("/activityQa")
@Slf4j
public class ActivityQaController {

    @Resource
    private TokenService tokenService;

    @Resource
    private ActivityQaService activityQaService;

    @PostMapping("/queryAll")
    public ResponseDto<Page<ActivityQa>> queryAllQa(@RequestHeader("token") String token, Page page) {
        //权限验证
        ResponseDto<Page<ActivityQa>> responseDto = ResponseDto.successDto();
        try {
            ActivityQaQuery activityQaQuery = new ActivityQaQuery();
            CommonLoginContext contextByken = tokenService.getContextByken(token);
            if (contextByken.getSourceCode().equals(SourceType.SPONSOR.getCode())) {
                Long userId = contextByken.getUserId();
                activityQaQuery.setSponsorId(Integer.valueOf(userId.intValue()));
            }
            return responseDto.successData(activityQaService.findByCondition(activityQaQuery, page));
        } catch (BizException e) {

            log.error("查询问答题异常，原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("查询问答题异常", e);
            return responseDto.failSys();
        }
    }

    @PostMapping("/add")
    public ResponseDto<Integer> addActivityQa(@RequestHeader("token") String token, ActivityQa activityQa) {
        //权限验证
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        CommonLoginContext contextByken = tokenService.getContextByken(token);
        activityQa.setSponsorId(Integer.valueOf(contextByken.getUserId().intValue()));
        try {
            BizAssert.notNull(activityQa.getSponsorId(), BizCode.PARAM_NULL.getMessage());
            return responseDto.successData(activityQaService.insert(activityQa));
        } catch (BizException e) {

            log.error("创建问答题异常，参数:{},原因：{}", activityQa.getId(), e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("创建问答题异常，参数:{}", activityQa.getId(), e);
            return responseDto.failSys();
        }
    }

    @PostMapping("/updateById")
    public ResponseDto<Boolean> updateById(@RequestHeader("token") String token, ActivityQa activity) {

        //权限验证
        ResponseDto<Boolean> responseDto = ResponseDto.successDto();

        try {
            BizAssert.notNull(activity.getId(), BizCode.PARAM_NULL.getMessage());
            CommonLoginContext contextByken = tokenService.getContextByken(token);
            if (contextByken.getSourceCode().equals(SourceType.SPONSOR.getCode())) {
                activity.setSponsorId(contextByken.getUserId().intValue());

            }
            activityQaService.updateById(activity);
            return responseDto.successData(true);
        } catch (BizException e) {

            log.error("更新问答题异常，参数:{},原因：{}", activity.getId(), e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("更新问答题异常，参数:{}", activity.getId(), e);
            return responseDto.failSys();
        }
    }

    @PostMapping("/del")
    public ResponseDto<Integer> delActivityQa(@RequestHeader("token") String token, @RequestParam("ids") List<Long> ids) {
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        try {
            return responseDto.successData(activityQaService.deleteById(ids));
        } catch (BizException e) {
            log.error("sponsor.del，原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("sponsor.del", e);
            return responseDto.failSys();
        }
    }

    @PostMapping({"/adds"})
    public ResponseDto<Integer> addActivityQas(@RequestHeader("token") String token, String list) {
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        try {
            System.out.println(list);
            List<ActivityQa> activityQas = JSONArray.parseArray(list, ActivityQa.class);
            System.out.println(activityQas);
            return responseDto.successData(activityQaService.inserts(token, activityQas));
        } catch (BizException e) {
            log.error("批量创建问答题异常， 参数:{},原因:{}", list, e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("批量创建问答题异常，参数:{}", list, e);
            return responseDto.failSys();
        }
    }

    @PostMapping({"/queryOne"})
    public ResponseDto<ActivityQa> queryOne(@RequestHeader("token") String token, Activity a) {
        ResponseDto<ActivityQa> responseDto = ResponseDto.successDto();
        try {
            return responseDto.successData(this.activityQaService.queryOne(a.getId()));
        } catch (BizException e) {
            log.error("查询问答题异常, 原因:{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("查询问答题异常", e);
            return responseDto.failSys();
        }
    }
}
