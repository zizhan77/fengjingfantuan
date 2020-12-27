package com.mem.vo.controller.organizer;

import com.mem.vo.business.base.model.po.BasicPlace;
import com.mem.vo.business.base.model.po.BasicPlaceQuery;
import com.mem.vo.business.base.model.po.MtaBean;
import com.mem.vo.business.base.service.BasicPlaceService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Slf4j
@Controller
@RequestMapping("/organizer")
public class organizerController {
    @Resource
    private BasicPlaceService basicPlaceService;
    @PostMapping("/space/queryBy")
    @ResponseBody
    public ResponseDto<Page<BasicPlace>> queryBy(@RequestHeader("token") String token, BasicPlaceQuery basicPlaceQuery, Page page) {
        ResponseDto<Page<BasicPlace>> responseDto = ResponseDto.successDto();
        try {
            Page<BasicPlace> list = basicPlaceService.findPageByCondition(page, basicPlaceQuery);
            return responseDto.successData(list);
        } catch (BizException e) {
            log.error("查询场地信息业务异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("查询场地信息异常", e);
            return responseDto.failSys();
        }
    }

    @PostMapping("/data/history")
    @ResponseBody
    public ResponseDto<MtaBean> history(String startTime,String endTime) {
        ResponseDto<MtaBean> responseDto = ResponseDto.successDto();
        try {
            MtaBean data = basicPlaceService.findHistory(startTime,endTime);
            return responseDto.successData(data);
        } catch (BizException e) {
            log.error("查询活动历史统计信息业务异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("查询活动历史统计信息异常", e);
            return responseDto.failSys();
        }
    }
}
