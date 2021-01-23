package com.mem.vo.controller.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mem.vo.business.base.model.po.*;
import com.mem.vo.business.base.model.vo.PlaceArtistVO;
import com.mem.vo.business.base.service.*;
import com.mem.vo.business.biz.model.vo.BasicCommonVo;
import com.mem.vo.business.biz.model.vo.performance.BasicPlaceRequest;
import com.mem.vo.business.biz.model.vo.performance.BasicPlaceVo;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.constant.VmOptionType;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.BeanCopyUtil;
import com.mem.vo.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author litongwei
 * @description:
 * @date 2019/5/26 17:29
 */

@RestController
@RequestMapping("/basic")
@Slf4j
public class BasicPlaceController {
    @Resource
    private PlaceArtistService placeArtistService;

    @Resource
    private BasicPlaceService basicPlaceService;

    @Resource
    private BasicArtistService basicArtistService;

    @Resource
    private BasicAddressService basicAddressService;


    @PostMapping("/place/getCityList")
    public ResponseDto<List<BasicCommonVo>> getCityList(@RequestHeader("token") String token) {
        ResponseDto<List<BasicCommonVo>> responseDto = ResponseDto.successDto();

        try {
            List<String> citylist =  basicPlaceService.findAllCity();
            List<BasicCommonVo> resList = new ArrayList<>();
            citylist.forEach(item -> {
                BasicCommonVo commonVo = new BasicCommonVo();
                BasicAddressQuery query = new BasicAddressQuery();
                query.setAddressCode(item);
                query.setLevel(2);
                List<BasicAddress> addressList = basicAddressService.findByCondition(query);
                BizAssert.notEmpty(addressList, BizCode.BIZ_1008.getMessage()+" "+item);
                commonVo.setName(addressList.get(0).getAddressName());
                commonVo.setCode(item);
                resList.add(commonVo);
            });
            return responseDto.successData(resList);

        } catch (BizException e) {

            log.error("获取场地城市列表异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("获取场地城市列表异常", e);
            return responseDto.failSys();

        }

    }


    @PostMapping("/place/getPlaceList")
    public ResponseDto<JSONObject> getPlaceList(@RequestHeader("token") String token,
        BasicPlaceRequest request) {
        ResponseDto<JSONObject> responseDto = ResponseDto.successDto();

        try {
            JSONObject jsonObject = new JSONObject();

            BasicPlaceQuery query = new BasicPlaceQuery();
            query.setName(request.getPlaceName());
            query.setEnable(request.getEnable());
            query.setTwoAddress(request.getCityId());
            Page page = new Page();
            page.setPage(request.getPage());
            page.setLimit(request.getLimit());

            Page<BasicPlace> resPage = basicPlaceService.findPageByCondition(page, query);
            if (CollectionUtils.isNotEmpty(resPage.getData())) {
                List<BasicPlace> placeList = resPage.getData();
                List<BasicPlaceVo> resList = new ArrayList<>();
                placeList.forEach(item -> {
                    BasicPlaceVo commonVo = BeanCopyUtil.copyProperties(item, BasicPlaceVo.class);
                    if (StringUtils.isNotBlank(commonVo.getOneAddress())) {
                        commonVo.setOneAddressName(basicAddressService.findNameByCode(commonVo.getOneAddress()));
                    }
                    if (StringUtils.isNotBlank(commonVo.getTwoAddress())) {
                        commonVo.setTwoAddressName(basicAddressService.findNameByCode(commonVo.getTwoAddress()));
                    }
                    if (StringUtils.isNotBlank(commonVo.getThreeAddress())) {
                        commonVo.setThreeAddressName(basicAddressService.findNameByCode(commonVo.getThreeAddress()));
                    }
                    List<String> artistList = placeArtistService.findByPlaceId(item.getId());
                    System.out.println(1);
//                    System.out.println(commonVo.getArtistId());
//                    if (commonVo.getArtistId()!=null) {
//                        commonVo.setArtistNmae(basicArtistService.findById(commonVo.getArtistId().longValue()).getArtistName());
//                    }
                    if (!artistList.isEmpty()) {
                        List<PlaceArtistVO> list = basicArtistService.findByIdList(artistList);
                        System.out.println(2);
                        commonVo.setArtistList(list);
                        System.out.println(3);
                    }
                    resList.add(commonVo);
                });
                System.out.println(123);
                jsonObject.put("total", resPage.getTotal());
                jsonObject.put("list", resList);

                return responseDto.successData(jsonObject);
            }
            return responseDto;

        } catch (BizException e) {

            log.error("根据城市获取场地列表异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("根据城市获取场地列表异常", e);
            return responseDto.failSys();

        }


    }

    @PostMapping("/place/addOrUpdatePlace")
    public ResponseDto<Boolean> addPlace(String str, Integer optionType, String list) {
        ResponseDto<Boolean> responseDto = ResponseDto.successDto();
        System.out.println(str);
        System.out.println(optionType);
        System.out.println(list);

        try {
            if (str == null && "".equals(str)) {
                throw new BizException(BizCode.PARAM_NULL.getMessage());
            }
            BasicPlaceVo basicPlaceVo = JsonUtil.fromJson(str, BasicPlaceVo.class);
            if (list != null && !"".equals(list)) {
                List<PlaceArtistVO> list2 = JSONArray.parseArray(list, PlaceArtistVO.class);
                System.out.println(list2);
                basicPlaceVo.setArtistList(list2);
            }

            if (VmOptionType.INSERT.getCode().equals(optionType)) {
                basicPlaceService.insert(basicPlaceVo);
            } else {
                basicPlaceService.updateById(basicPlaceVo);
            }
            return responseDto.successData(true);

        } catch (BizException e) {

            log.error("保存场地业务异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("保存场地异常", e);
            return responseDto.failSys();

        }


    }


    @PostMapping("/place/queryDetail")
    public ResponseDto<BasicPlaceVo> queryDetail(@RequestHeader("token") String token, String placeId) {
        ResponseDto<BasicPlaceVo> responseDto = ResponseDto.successDto();

        try {
            BasicPlaceQuery query = new BasicPlaceQuery();
            query.setId(Long.valueOf(placeId));
            List<BasicPlace> list = basicPlaceService.findByCondition(query);
            BizAssert.notEmpty(list, BizCode.BIZ_1013.getMessage());
            BasicPlaceVo vo = getBasicPlaceVo(list.get(0));
            List<String> artistIdList = placeArtistService.findByPlaceId(list.get(0).getId());
            List<PlaceArtistVO> artistList = basicArtistService.findByIdList(artistIdList);
            vo.setArtistList(artistList);
            return responseDto.successData(vo);

        } catch (BizException e) {

            log.error("查询场地详情,业务异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("查询场地详情,系统异常", e);
            return responseDto.failSys();

        }


    }


    @PostMapping("/place/queryList")
    public ResponseDto<List<BasicPlaceVo>> queryList(@RequestHeader("token") String token, String placeId) {
        ResponseDto<List<BasicPlaceVo>> responseDto = ResponseDto.successDto();

        try {
            BasicPlaceQuery query = new BasicPlaceQuery();
            List<BasicPlace> list = basicPlaceService.findByCondition(query);
            BizAssert.notEmpty(list, BizCode.BIZ_1013.getMessage());

            List<BasicPlaceVo> basicPlaceVos = list.stream().map(item ->
                getBasicPlaceVo(item)
            ).collect(Collectors.toList());

            return responseDto.successData(basicPlaceVos);

        } catch (BizException e) {

            log.error("查询场地详情,业务异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("查询场地详情,系统异常", e);
            return responseDto.failSys();

        }


    }

    private BasicPlaceVo getBasicPlaceVo(BasicPlace basicPlace) {
        BasicPlaceVo vo = BeanCopyUtil.copyProperties(basicPlace, BasicPlaceVo.class);
        if (StringUtils.isNotBlank(vo.getOneAddress())) {
            vo.setOneAddressName(basicAddressService.findNameByCode(vo.getOneAddress()));
        }

        if (StringUtils.isNotBlank(vo.getTwoAddress())) {
            vo.setTwoAddressName(basicAddressService.findNameByCode(vo.getTwoAddress()));
        }

        if (StringUtils.isNotBlank(vo.getThreeAddress())) {
            vo.setThreeAddressName(basicAddressService.findNameByCode(vo.getThreeAddress()));
        }
        return vo;
    }


    @PostMapping("/deleteById")
    public ResponseDto<Boolean> deleteById(@RequestHeader("token") String token, String placeId) {
        ResponseDto<Boolean> responseDto = ResponseDto.successDto();

        try {

            basicPlaceService.deleteById(Long.valueOf(placeId));
            return responseDto.successData(true);

        } catch (BizException e) {

            log.error("删除场地业务异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("删除场地异常", e);
            return responseDto.failSys();

        }
    }


}
