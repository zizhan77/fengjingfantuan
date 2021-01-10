package com.mem.vo.controller;

import com.mem.vo.business.base.service.ActorServise;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/click"})
public class ClickController {
    private static final Logger log = LoggerFactory.getLogger(ClickController.class);

    @Resource
    private ActorServise actorServise;

    @PostMapping({"/phone/clickBanner"})
    public ResponseDto<Integer> clickBanner(String tokenId, Long id) {
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        try {
            BizAssert.notNull(id, BizCode.PARAM_NULL.getMessage());
            Integer ilst = actorServise.clickBanner(tokenId, id);
            return responseDto.successData(ilst);
        } catch (Exception e) {
            log.debug("clickBanner", e.getMessage());
            responseDto.failData("有问题");
            return responseDto;
        }
    }

    @PostMapping({"/phone/clickPrize"})
    public ResponseDto<Integer> clickPrize(String tokenId, Long id) {
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        try {
            BizAssert.notNull(id, BizCode.PARAM_NULL.getMessage());
            Integer ilst = actorServise.clickPrize(tokenId, id);
            return responseDto.successData(ilst);
        } catch (Exception e) {
            log.debug("clickPrize", e.getMessage());
            responseDto.failData("有问题");
            return responseDto;
        }
    }

    @PostMapping({"/phone/clickSponsor"})
    public ResponseDto<Integer> clickSponsor(String tokenId, Long id) {
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        try {
            BizAssert.notNull(id, BizCode.PARAM_NULL.getMessage());
            Integer ilst = actorServise.clickSponsor(tokenId, id);
            return responseDto.successData(ilst);
        } catch (Exception e) {
            log.debug("clickSponsor", e.getMessage());
            responseDto.failData("有问题");
            return responseDto;
        }
    }
}
