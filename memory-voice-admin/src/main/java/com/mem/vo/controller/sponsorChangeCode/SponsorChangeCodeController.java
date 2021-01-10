package com.mem.vo.controller.sponsorChangeCode;

import com.mem.vo.business.base.service.ChangeCodeService;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizException;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping({"/changeCode"})
public class SponsorChangeCodeController {
    private static final Logger log = LoggerFactory.getLogger(SponsorChangeCodeController.class);

    @Resource
    private ChangeCodeService changeCodeService;

    @PostMapping({"/up"})
    public ResponseDto<Integer> up(@RequestHeader("token") String token, MultipartFile file, String codeTypeId) {
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        try {
            return responseDto.successData(changeCodeService.up(codeTypeId, file));
        } catch (BizException e) {
            log.error("批量上传优惠券码异常, 参数:{},原因: {}", file, e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("批量上传优惠券码异常, 参数:{}", file, e);
            return responseDto.failSys();
        }
    }
}
