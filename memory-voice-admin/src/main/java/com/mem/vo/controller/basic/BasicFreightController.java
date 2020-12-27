package com.mem.vo.controller.basic;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.mem.vo.business.base.model.po.BasicAddress;
import com.mem.vo.business.base.model.po.BasicAddressQuery;
import com.mem.vo.business.base.model.po.BasicFreight;
import com.mem.vo.business.base.model.po.BasicFreightQuery;
import com.mem.vo.business.base.service.BasicAddressService;
import com.mem.vo.business.base.service.BasicFreightService;
import com.mem.vo.business.biz.model.vo.performance.BasicFreightVo;
import com.mem.vo.business.biz.model.vo.performance.FreightInfoVo;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.BeanCopyUtil;
import com.mem.vo.config.annotations.CommonExHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author litongwei
 * @description:
 * @date 2019/6/3 15:21
 */

@RestController
@RequestMapping("/basic/freight")
@Slf4j
public class BasicFreightController {

    @Resource
    private BasicFreightService basicFreightService;

    @Resource
    private BasicAddressService basicAddressService;

    @PostMapping("/updateById")
    public ResponseDto<Boolean> updateById(@RequestHeader("token") String token, String freightId, BigDecimal freight) {
        ResponseDto<Boolean> responseDto = ResponseDto.successDto();
        try {

            BasicFreight basicFreight = new BasicFreight();
            basicFreight.setId(Long.valueOf(freightId));
            basicFreight.setFreight(freight);
            basicFreightService.updateById(basicFreight);
            return responseDto.successData(true);

        } catch (BizException e) {

            log.error("修改运费业务异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("修改运费异常", e);
            return responseDto.failSys();

        }
    }

    @PostMapping("/deleteById")
    public ResponseDto<Boolean> deleteById(@RequestHeader("token") String token, String freightId) {
        ResponseDto<Boolean> responseDto = ResponseDto.successDto();

        try {

            basicFreightService.deleteById(Long.valueOf(freightId));
            return responseDto.successData(true);

        } catch (BizException e) {

            log.error("删除运费业务异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("删除运费异常", e);
            return responseDto.failSys();

        }
    }


    @PostMapping("/queryInfo")
    public ResponseDto<FreightInfoVo> queryInfo(@RequestHeader("token") String token, Page page) {

        ResponseDto<FreightInfoVo> responseDto = ResponseDto.successDto();

        try {
            FreightInfoVo infoVo = new FreightInfoVo();
            BasicFreightQuery query = new BasicFreightQuery();
            query.setIsDelete(0);
            Page<BasicFreight> resPage = basicFreightService.findPageByCondition(page, query);
            List<BasicFreight> list = resPage.getData();
            if (CollectionUtils.isNotEmpty(list)) {

                Optional<BasicFreight> freightA = list.stream().filter(item -> "-999".equals(item.getOneAddress()))
                    .findFirst();

                if (freightA.isPresent()) {
                    BasicFreightVo vo = BeanCopyUtil.copyProperties(freightA.get(), BasicFreightVo.class);
                    assembleAddress(vo);
                    infoVo.setDefaultFreight(vo);
                }

                List<BasicFreight> specialFreights =
                    list.stream().filter(item -> !"-999".equals(item.getOneAddress())).collect(
                        Collectors.toList());

                if (CollectionUtils.isNotEmpty(specialFreights)) {

                    List<BasicFreightVo> voList = specialFreights.stream().map(item ->
                    {
                        BasicFreightVo vo = BeanCopyUtil.copyProperties(item, BasicFreightVo.class);
                        assembleAddress(vo);
                        return vo;
                    }).collect(Collectors.toList());

                    infoVo.setFreightVoList(voList);
                    infoVo.setTotal(resPage.getTotal());
                }
            }

            return responseDto.successData(infoVo);

        } catch (BizException e) {

            log.error("删除运费业务异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("删除运费异常", e);
            return responseDto.failSys();

        }


    }

    private void assembleAddress(BasicFreightVo vo) {
        if (StringUtils.isNotBlank(vo.getOneAddress())) {
            vo.setOneAddressName(basicAddressService.findNameByCode(vo.getOneAddress()));
        }
    }


    @RequestMapping("/init")
    @Transactional
    public void initRecord(){
        BasicAddressQuery query = new BasicAddressQuery();
        query.setLevel(1);
        query.setIsDelete(1);
        List<BasicAddress> basicAddresses = basicAddressService.findByCondition(query);
        for (BasicAddress basicAddress:basicAddresses){
            BasicFreight basicFreight = new BasicFreight();
            basicFreight.setOneAddress(basicAddress.getAddressCode());
            basicFreight.setFreight(new BigDecimal("-1"));
            basicFreightService.insert(basicFreight);
        }
    }

    @PostMapping("/queryFreight")
    @CommonExHandler(key = "运费查询接口")
    public ResponseDto<BigDecimal> queryFreight(@RequestHeader("token") String token,String oneAddressCode){

        ResponseDto<BigDecimal> responseDto = new ResponseDto();

        responseDto.setData(basicFreightService.getFreightByOneAddress(oneAddressCode));
        return responseDto;
    }

}
