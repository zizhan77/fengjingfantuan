package com.mem.vo.controller.activity;

import com.mem.vo.business.base.model.po.Activitycount;
import com.mem.vo.business.base.model.vo.ActivitycountVO;
import com.mem.vo.business.base.service.ActivityCountService;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizException;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/activityCount"})
public class ActivityCountController {
    private static final Logger log = LoggerFactory.getLogger(ActivityCountController.class);

    @Resource
    private ActivityCountService activityCountService;

    public String a() {
        this.activityCountService.a();
        return "ok";
    }

    @PostMapping({"/add"})
    public ResponseDto<String> add(@RequestHeader("token") String token, String activityId) {
        ResponseDto<String> responseDto = ResponseDto.successDto();
        try {
            return responseDto.successData(activityCountService.add(token, activityId));
        } catch (BizException e) {
            log.error("更新活动统计信息异常, 原因:{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("更新活动统计信息异常", e);
            return responseDto.failSys();
        }
    }

    @PostMapping({"/query"})
    public ResponseDto<List<ActivitycountVO>> queryPv(String date, Activitycount activitycount) {
        ResponseDto<List<ActivitycountVO>> responseDto = ResponseDto.successDto();
        try {
            return responseDto.successData(activityCountService.query(date, activitycount));
        } catch (BizException e) {
            log.error("查询活动统计信息异常, 原因:{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("查询活动统计信息异常", e);
            return responseDto.failSys();
        }
    }

    @PostMapping({"/queryByName"})
    public ResponseDto<Page<ActivitycountVO>> queryUv(Page page, String activityName) {
        ResponseDto<Page<ActivitycountVO>> responseDto = ResponseDto.successDto();
        try {
            return responseDto.successData(activityCountService.queryByName(page, activityName));
        } catch (BizException e) {
            log.error("查询活动统计信息异常, 原因:{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("查询活动统计信息异常", e);
            return responseDto.failSys();
        }
    }
}
