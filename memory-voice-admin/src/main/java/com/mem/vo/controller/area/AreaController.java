package com.mem.vo.controller.area;

import com.mem.vo.business.base.model.po.Area;
import com.mem.vo.business.base.model.po.AreaQuery;
import com.mem.vo.business.base.service.AreaService;
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
 * @author litongwei
 * @description: 演出
 * @date 2019/5/25 15:43
 */

@RestController
@RequestMapping("/area")
@Slf4j
public class AreaController {

    @Resource
    private AreaService areaService;

    @PostMapping("/queryAreaListByPlaceId")
    public ResponseDto<List<Area>> queryAreaListByPlaceId(@RequestHeader("token") String token,AreaQuery areaQuery) {

        //权限验证
        ResponseDto<List<Area>> responseDto = ResponseDto.successDto();

        try {
            BizAssert.notNull(areaQuery.getPlaceId(), BizCode.PARAM_NULL.getMessage());
            return responseDto.successData(areaService.findByCondition(areaQuery));

        } catch (BizException e) {
            log.error("获取区域列表异常，参数:{},原因：{}", areaQuery.getPlaceId(), e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("获取区域列表异常，参数:{}", areaQuery.getPlaceId(), e);
            return responseDto.failSys();
        }
    }
    @PostMapping("/createArea")
    public ResponseDto<Integer> createArea(@RequestHeader("token") String token, Area area) {

        //权限验证
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        try {
            BizAssert.notNull(area.getPlaceId(), BizCode.PARAM_NULL.getMessage());
            //创建区域
            return responseDto.successData(areaService.insert(area));
        } catch (BizException e) {

            log.error("创建区域异常，参数:{},原因：{}", area.getPlaceId(), e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("创建区域异常，参数:{}", area.getPlaceId(), e);
            return responseDto.failSys();
        }
    }
    @PostMapping("/updateArea")
    public ResponseDto<Integer> updateArea(@RequestHeader("token") String token, Area area) {

        //权限验证
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        try {
            BizAssert.notNull(area.getPlaceId(), BizCode.PARAM_NULL.getMessage());
            //创建区域
            return responseDto.successData(areaService.updateById(area));
        } catch (BizException e) {

            log.error("修改区域异常，参数:{},原因：{}", area.getPlaceId(), e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("修改区域异常，参数:{}", area.getPlaceId(), e);
            return responseDto.failSys();
        }
    }
}
