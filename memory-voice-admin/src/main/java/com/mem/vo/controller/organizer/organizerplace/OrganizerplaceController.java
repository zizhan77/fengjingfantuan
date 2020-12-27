package com.mem.vo.controller.organizer.organizerplace;

import com.mem.vo.business.base.service.OrganizerPlaceService;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
@Slf4j
@RestController
@RequestMapping("/organizerPlace")
public class OrganizerplaceController {

    @Resource
    private OrganizerPlaceService organizerPlaceService;
    @PostMapping("/place/tellMe")
    public ResponseDto<String> tellMe(@RequestHeader("token")String token, String placeId){


        System.out.println(placeId);
        ResponseDto<Boolean> responseDto = ResponseDto.successDto();

        try {

            organizerPlaceService.insert(token,placeId);
            return responseDto.successData(true);

        } catch (BizException e) {
            log.error("添加场地主办方关联信息业务异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("添加场地主办方关联信息异常", e);
            return responseDto.failSys();
        }

    }


}
