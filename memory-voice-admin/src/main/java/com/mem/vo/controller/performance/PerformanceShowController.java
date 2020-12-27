package com.mem.vo.controller.performance;

import javax.annotation.Resource;

import com.mem.vo.business.base.model.po.PerformanceShow;
import com.mem.vo.business.base.service.PerformanceShowService;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <br>
 *    场次
 * <b>功能：</b>PerformanceShowController<br>
 * <br>
 */
@RestController
@RequestMapping("/performance-show")
@Slf4j
public class PerformanceShowController {

    @Resource
    private PerformanceShowService performanceShowService;

    @PostMapping("/addShow")
    public ResponseDto<Long> addShow(@RequestHeader String token,
        PerformanceShow performanceShow) {
        ResponseDto<Long> responseDto = ResponseDto.successDto();

        try {
            if(performanceShow.getStartTimeStr()!=null){
                performanceShow.setStartTime(DateUtil.parseDatetime(performanceShow.getStartTimeStr()));
            }

            if(performanceShow.getEndTimeStr()!=null){
                performanceShow.setEndTime(DateUtil.parseDatetime(performanceShow.getEndTimeStr()));
            }

            performanceShowService.insert(performanceShow);
            responseDto.successData(performanceShow.getId());
            return responseDto;
        } catch (BizException e) {
            log.error("添加场次异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("添加场次系统异常", e);
            return responseDto.failSys();
        }
    }

    @PostMapping("/deleteShow")
    public ResponseDto<Boolean> deleteShow(@RequestHeader String token,
        Long showId) {
        ResponseDto<Boolean> responseDto = ResponseDto.successDto();

        try {
            performanceShowService.deleteById(showId);
            responseDto.successData(true);
            return responseDto;
        } catch (BizException e) {
            log.error("删除场次异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("删除场次系统异常", e);
            return responseDto.failSys();
        }
    }


}
