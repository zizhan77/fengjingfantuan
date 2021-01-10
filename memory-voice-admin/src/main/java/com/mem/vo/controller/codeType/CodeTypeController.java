package com.mem.vo.controller.codeType;

import com.mem.vo.business.base.model.po.CodeType;
import com.mem.vo.business.base.service.CodeTypeService;
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
@RequestMapping({"/codeType"})
public class CodeTypeController {
    private static final Logger log = LoggerFactory.getLogger(CodeTypeController.class);

    @Resource
    private CodeTypeService codeTypeService;

    @PostMapping({"/edit"})
    public ResponseDto<CodeType> queryQa(@RequestHeader("token") String token, CodeType codeType) {
        ResponseDto<CodeType> responseDto = ResponseDto.successDto();
        try {
            return responseDto.successData(codeTypeService.edit(codeType));
        } catch (BizException e) {
            log.error(", 参数:{},原因:{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("参数{}", e);
            return responseDto.failSys();
        }
    }

    @PostMapping({"/queryBySponsorId"})
    public ResponseDto<List<CodeType>> queryBySponsorId(@RequestHeader("token") String token, String ids) {
        ResponseDto<List<CodeType>> responseDto = ResponseDto.successDto();
        try {
            if (ids == null || "".equals(ids)) {
                throw new BizException("");
            }
            return responseDto.successData(codeTypeService.queryBySponsorId(ids));
        } catch (BizException e) {
            log.error(", 参数:{},原因:{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("参数:{}", e);
            return responseDto.failSys();
        }
    }

    @PostMapping({"/queryById"})
    public ResponseDto<CodeType> queryById(@RequestHeader("token") String token, String id) {
        ResponseDto<CodeType> responseDto = ResponseDto.successDto();
        try {
            if (id == null || "".equals(id)) {
                throw new BizException("");
            }
            return responseDto.successData(codeTypeService.queryById(Integer.valueOf(Integer.parseInt(id))));
        } catch (BizException e) {
            log.error(", 参数:{},原因:{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("参数:{}", e);
            return responseDto.failSys();
        }
    }
}
