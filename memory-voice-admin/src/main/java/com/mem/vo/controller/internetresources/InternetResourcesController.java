package com.mem.vo.controller.internetresources;

import com.mem.vo.business.base.model.po.InternetResources;
import com.mem.vo.business.base.model.po.InternetResourcesQuery;
import com.mem.vo.business.base.service.InternetResourcesService;
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
 * @date 2019-06-19 20:11
 */
@RestController
@RequestMapping("/internetResources")
@Slf4j
public class InternetResourcesController {

    @Resource
    private InternetResourcesService internetResourcesService;

    @Deprecated
    public ResponseDto<List<InternetResources>> queryFileByType(Integer type) {
        //权限验证
        ResponseDto<List<InternetResources>> responseDto = ResponseDto.successDto();
        try {
            BizAssert.notNull(type, BizCode.PARAM_NULL.getMessage());
            //先这样，懒得写sql了。
            InternetResourcesQuery query = new InternetResourcesQuery();
            query.setType(type);
            return responseDto.successData(internetResourcesService.findByCondition(query));

        } catch (BizException e) {

            log.error("获取资源信息异常，参数:{},原因：{}", type, e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("获取资源信息异常，参数:{}", type, e);
            return responseDto.failSys();
        }

    }

    @PostMapping("/queryByType")
    public ResponseDto<List<InternetResources>> queryQiniuInfo(Integer type) {
        //权限验证
        ResponseDto<List<InternetResources>> responseDto = ResponseDto.successDto();
        try {
            BizAssert.notNull(type, BizCode.PARAM_NULL.getMessage());
            return responseDto.successData(internetResourcesService.findQiniuResources(type));

        } catch (BizException e) {
            log.error("获取资源信息异常，参数:{},原因：{}", type, e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("获取资源信息异常，参数:{}", type, e);
            return responseDto.failSys();
        }
    }
}
