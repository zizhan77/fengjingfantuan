package com.mem.vo.controller.basic;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.mem.vo.business.base.model.po.BasicAddress;
import com.mem.vo.business.base.model.po.BasicAddressQuery;
import com.mem.vo.business.base.service.BasicAddressService;
import com.mem.vo.business.biz.model.vo.basic.BasicAddressNode;
import com.mem.vo.business.biz.model.vo.basic.BasicAddressRequest;
import com.mem.vo.business.biz.model.vo.basic.BasicAddressVo;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.BeanCopyUtil;
import com.mem.vo.common.util.HttpClientUtils;
import com.mem.vo.config.annotations.CommonExHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author litongwei
 * @description:
 * @date 2019/6/3 16:58
 */

@RestController
@RequestMapping("/basic/address")
@Slf4j
public class BasicAddressController {

    @Resource
    private BasicAddressService basicAddressService;

    @PostMapping("/queryCityList")
    public ResponseDto<JSONObject> queryCityList(@RequestHeader("token") String token, BasicAddressRequest request) {

        ResponseDto<JSONObject> responseDto = ResponseDto.successDto();

        try {
            JSONObject jsonObject = new JSONObject();
            BasicAddressQuery query = new BasicAddressQuery();
            query.setIsDelete(0);
            query.setLevel(2);
            query.setAddressName(request.getCityName());
            query.setStatus(request.getStatus());
            Page page = new Page();
            page.setPage(request.getPage());
            page.setLimit(request.getLimit());
            Page<BasicAddress> resPage = basicAddressService.findPageByCondition(page, query);
            List<BasicAddress> basicAddresses = resPage.getData();
            if (CollectionUtils.isNotEmpty(basicAddresses)) {
//                List<BasicAddressVo> voList = basicAddresses.stream().map(item -> {
//                    BasicAddressVo vo = BeanCopyUtil.copyProperties(item, BasicAddressVo.class);
//                    return vo;
//                }).collect(Collectors.toList());
                List<BasicAddressVo> voList = basicAddresses.stream().map(item ->
                        BeanCopyUtil.copyProperties(item, BasicAddressVo.class)).collect(Collectors.toList());

                jsonObject.put("total",resPage.getTotal());
                jsonObject.put("list",voList);

                return responseDto.successData(jsonObject);
            }
            return responseDto;

        } catch (BizException e) {

            log.error("查询城市列表异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("查询城市列表系统异常", e);
            return responseDto.failSys();

        }

    }


    @PostMapping("/updateCityStatus")
    public ResponseDto<Boolean> updateCityStatus(@RequestHeader("token") String token, String cityId, Integer status) {

        ResponseDto<Boolean> responseDto = ResponseDto.successDto();

        try {

            BizAssert.hasText(cityId,"id 为空，参数错误");
            BasicAddress basicAddress = new BasicAddress();
            basicAddress.setStatus(status);
            basicAddress.setId(Long.valueOf(cityId));
            basicAddressService.updateById(basicAddress);

            return responseDto.successData(true);

        } catch (BizException e) {

            log.error("修改城市状态异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("修改城市状态系统异常", e);
            return responseDto.failSys();

        }

    }


    @Deprecated
    @PostMapping("/getAllAddrTree")
    public ResponseDto<List<BasicAddressNode>> getAllAddrTree(@RequestHeader("token") String token) {

        ResponseDto<List<BasicAddressNode>> responseDto = ResponseDto.successDto();

        try {

            BasicAddressQuery query = new BasicAddressQuery();
            query.setLevel(1);
            List<BasicAddress> addressList = basicAddressService.findByCondition(query);
            List<BasicAddressNode> nodes = getBasicAddressNodes(addressList);

            nodes.forEach(item -> {
                BasicAddressQuery query2 = new BasicAddressQuery();
                query.setLevel(2);
                List<BasicAddress> addressList2 = basicAddressService.findByCondition(query2);
                if (CollectionUtils.isNotEmpty(addressList2)) {
                    List<BasicAddressNode> nodes2 = getBasicAddressNodes(addressList2);
                    nodes2.forEach(item2 -> {
                        BasicAddressQuery query3 = new BasicAddressQuery();
                        query.setLevel(3);
                        List<BasicAddress> addressList3 = basicAddressService.findByCondition(query3);
                        if (CollectionUtils.isNotEmpty(addressList3)) {
                            List<BasicAddressNode> nodes3 = getBasicAddressNodes(addressList3);
                            item2.setChildren(nodes3);
                        }
                    });
                    item.setChildren(nodes2);
                }

            });

            return responseDto.successData(nodes);

        } catch (BizException e) {

            log.error("获取地址树异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("获取地址树系统异常", e);
            return responseDto.failSys();

        }

    }

    private List<BasicAddressNode> getBasicAddressNodes(List<BasicAddress> addressList) {
//        return addressList.stream().map(item -> {
//
//            BasicAddressNode node = BeanCopyUtil.copyProperties(item, BasicAddressNode.class);
//            return node;
//
//        }).collect(Collectors.toList());
        return  addressList.stream().map(item ->
                BeanCopyUtil.copyProperties(item, BasicAddressNode.class)).collect(Collectors.toList());
    }


    @PostMapping("/queryListByParent")
    public ResponseDto<List<BasicAddressNode>> queryListByParent(@RequestHeader String token,String parentCode){

        ResponseDto<List<BasicAddressNode>> responseDto = ResponseDto.successDto();


        try {
            BasicAddressQuery query = new BasicAddressQuery();
            query.setParentCode(parentCode);
            List<BasicAddress> addressList = basicAddressService.findByCondition(query);

            List<BasicAddressNode> nodes = getBasicAddressNodes(addressList);
            return responseDto.successData(nodes);

        } catch (BizException e) {

            log.error("获取地址列表异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("获取地址列表异常", e);
            return responseDto.failSys();

        }

    }


    @GetMapping("initAddress")
    @CommonExHandler(key = "初始化地址表")
    public ResponseDto<Boolean> initAddress(){
        String AllAddress = HttpClientUtils.get("https://apis.map.qq.com/ws/district/v1/list?key=6IZBZ-3R3CI-CL2GE-5ZSP4-FFY4Q-MFF3J");


        return new ResponseDto<>().successData(true);
    }

}
