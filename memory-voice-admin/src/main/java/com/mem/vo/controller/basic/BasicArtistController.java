package com.mem.vo.controller.basic;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.mem.vo.business.base.model.po.BasicArtist;
import com.mem.vo.business.base.service.BasicAddressService;
import com.mem.vo.business.base.service.BasicArtistService;
import com.mem.vo.business.biz.model.vo.performance.BasicArtistRequest;
import com.mem.vo.business.biz.model.vo.performance.BasicArtistVo;
import com.mem.vo.common.constant.VmOptionType;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.BeanCopyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author litongwei
 * @description: 艺人管理
 * @date 2019/5/31 14:29
 */

@RestController
@RequestMapping("/basic/artist")
@Slf4j
public class BasicArtistController {

    @Resource
    private BasicArtistService basicArtistService;

    @Resource
    private BasicAddressService basicAddressService;


    @PostMapping("/addOrUpdate")
    public ResponseDto<Boolean> addOrUpdate(@RequestHeader("token") String token, BasicArtistVo basicArtistVo,
        Integer optType) {
        ResponseDto<Boolean> responseDto = ResponseDto.successDto();

        try {
            BasicArtist basicArtist = BeanCopyUtil.copyProperties(basicArtistVo, BasicArtist.class);
            if (VmOptionType.INSERT.getCode().equals(optType)) {
                basicArtistService.insert(basicArtist);
            } else {
                basicArtistService.updateById(basicArtist);
            }
            return responseDto.successData(true);

        } catch (BizException e) {

            log.error("保存艺人业务异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("保存艺人异常", e);
            return responseDto.failSys();

        }

    }


    @PostMapping("/queryArtistList")
    public ResponseDto<JSONObject> queryArtistList(@RequestHeader("token") String token,
        BasicArtistRequest artistRequest) {
        ResponseDto<JSONObject> responseDto = ResponseDto.successDto();

        try {

            JSONObject jsonObject = new JSONObject();


            Page page = new Page();
            page.setPage(artistRequest.getPage());
            page.setLimit(artistRequest.getLimit());

            Page<BasicArtist> artists = basicArtistService.findByName(page, artistRequest.getActorName());
            if (CollectionUtils.isEmpty(artists.getData())) {
                return responseDto.successData(null);
            }

            List<BasicArtistVo> voList = new ArrayList<>();
            artists.getData().forEach(item -> {
                BasicArtistVo vo = BeanCopyUtil.copyProperties(item, BasicArtistVo.class);
                assembleaddress(vo);

                voList.add(vo);
            });

            jsonObject.put("total", artists.getTotal());
            jsonObject.put("list", voList);

            return responseDto.successData(jsonObject);

        } catch (BizException e) {

            log.error("查询艺人业务异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("查询艺人异常", e);
            return responseDto.failSys();

        }

    }

    private void assembleaddress(BasicArtistVo vo) {
        if (StringUtils.isNotBlank(vo.getBirthOneAddress())) {
            vo.setBirthOneAddressName(basicAddressService.findNameByCode(vo.getBirthOneAddress()));
        }

        if (StringUtils.isNotBlank(vo.getBirthTwoAdddress())) {
            vo.setBirthTwoAddressName(basicAddressService.findNameByCode(vo.getBirthTwoAdddress()));
        }
    }

    @PostMapping("/queryArtistDetail")
    public ResponseDto<BasicArtistVo> queryArtistDetail(@RequestHeader("token") String token, Long actorId) {
        ResponseDto<BasicArtistVo> responseDto = ResponseDto.successDto();

        try {

            BasicArtist artist = basicArtistService.findById(actorId);
            if (artist != null) {
                BasicArtistVo vo = BeanCopyUtil.copyProperties(artist, BasicArtistVo.class);
                assembleaddress(vo);
                return responseDto.successData(vo);

            }
            return responseDto;

        } catch (BizException e) {

            log.error("查询艺人详情业务异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("查询艺人详情异常", e);
            return responseDto.failSys();

        }
    }


    @PostMapping("/deleteById")
    public ResponseDto<Boolean> deleteById(@RequestHeader("token") String token, String actorId) {
        ResponseDto<Boolean> responseDto = ResponseDto.successDto();

        try {

            basicArtistService.deleteById(Long.valueOf(actorId));
            return responseDto.successData(true);

        } catch (BizException e) {

            log.error("删除艺人业务异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("删除艺人异常", e);
            return responseDto.failSys();

        }
    }

}
