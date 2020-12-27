package com.mem.vo.controller.ticket;

import javax.annotation.Resource;

import com.mem.vo.business.base.model.po.TicketGear;
import com.mem.vo.business.base.service.TicketGearService;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <br>
 * <b>功能：</b>TicketGearController<br>
 * <br>
 */
@RestController
@RequestMapping("/ticket-gear")
@Slf4j
public class TicketGearController {

    @Resource
    private TicketGearService ticketGearService;

    @PostMapping("/addGear")
    public ResponseDto<Long> addGear(@RequestHeader String token,
        TicketGear ticketGear) {
        ResponseDto<Long> responseDto = ResponseDto.successDto();

        try {
            ticketGearService.insert(ticketGear);
            responseDto.successData(ticketGear.getId());
            return responseDto;
        } catch (BizException e) {
            log.error("添加票档异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("添加票档系统异常", e);
            return responseDto.failSys();
        }
    }


    @PostMapping("/deleteGear")
    public ResponseDto<Boolean> deleteGear(@RequestHeader String token,
        Long gearId) {
        ResponseDto<Boolean> responseDto = ResponseDto.successDto();

        try {
            ticketGearService.deleteById(gearId);
            responseDto.successData(true);
            return responseDto;
        } catch (BizException e) {
            log.error("删除票档异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("删除票档系统异常", e);
            return responseDto.failSys();
        }
    }


}
