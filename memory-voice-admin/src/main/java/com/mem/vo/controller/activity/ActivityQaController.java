package com.mem.vo.controller.activity;

import com.mem.vo.business.base.model.po.ActivityQa;
import com.mem.vo.business.base.model.po.ActivityQaQuery;
import com.mem.vo.business.base.service.ActivityQaService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private ActivityQaService activityQaService;

    @PostMapping("/queryAll")
    public ResponseDto<List<ActivityQa>> queryAllQa(@RequestHeader("token") String token) {
        //权限验证
        ResponseDto<List<ActivityQa>> responseDto = ResponseDto.successDto();
        try {
            return responseDto.successData(activityQaService.findByCondition(new ActivityQaQuery()));
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

        try {
            BizAssert.notNull(activityQa.getId(), BizCode.PARAM_NULL.getMessage());
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
}
