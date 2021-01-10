package com.mem.vo.controller.sponsor;

import com.mem.vo.business.base.model.po.MtaBean;
import com.mem.vo.business.base.model.po.MtaData;
import com.mem.vo.business.base.model.po.Sponsor;
import com.mem.vo.business.base.model.po.SponsorQuery;
import com.mem.vo.business.base.service.ActivityUserService;
import com.mem.vo.business.base.service.SponsorService;
import com.mem.vo.business.base.service.UserService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.config.annotations.CommonExHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


import java.util.HashMap;
import java.util.List;

/**
 * <br>
 * <b>功能：</b>SponsorController<br>
 * <br>
 */
@RestController
@RequestMapping("/sponsor")
@Slf4j
public class SponsorController {

    @Resource
    private UserService userService;

    @Resource
    private SponsorService sponsorService;

    @Resource
    private ActivityUserService activityUserService;


    @PostMapping({"/phone/getSponsorOne"})
    public ResponseDto<Sponsor> querySponorPictureByactid(Long id) {
        ResponseDto<Sponsor> responseDto = ResponseDto.successDto();
        try {
            return responseDto.successData(sponsorService.getSponsorOne(id));
        } catch (BizException e) {
            log.error("sponsor.queryAll, 原因:{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("sponsor.queryAll", e);
            return responseDto.failSys();
        }
    }

    @PostMapping({"/phone/queryAllSponorByPhone"})
    public ResponseDto<List<Sponsor>> queryAllSponorByPhone(Long spid, Long activityid) {
        ResponseDto<List<Sponsor>> responseDto = ResponseDto.successDto();
        try {
            return responseDto.successData(sponsorService.queryAllSponorByPhone(spid, activityid));
        } catch (BizException e) {
            log.error("sponsor.queryAll, 原因:{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("sponsor.queryAll", e);
            return responseDto.failSys();
        }
    }

    @PostMapping("/queryAll")
    public ResponseDto<List<Sponsor>> queryAllSponor(@RequestHeader("token") String token) {
        //权限验证
        ResponseDto<List<Sponsor>> responseDto = ResponseDto.successDto();
        try {
            return responseDto.successData(sponsorService.findByCondition(new SponsorQuery()));
        } catch (BizException e) {
            log.error("sponsor.queryAll，原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("sponsor.queryAll", e);
            return responseDto.failSys();
        }
    }

    @CommonExHandler(key = "添加赞助商")
    @PostMapping(value = "/add")
    public ResponseDto<Integer> addSave(@RequestHeader("token") String token, Sponsor sponsor) {
        //权限验证
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
            BizAssert.notNull(sponsor, BizCode.PARAM_NULL.getMessage());
            BizAssert.notNull(sponsor.getPhone(),BizCode.PARAM_NULL.getMessage());
            SponsorQuery query = new SponsorQuery();
            query.setPhone(sponsor.getPhone());
            List<Sponsor> sponsorList = sponsorService.findByCondition(query);
            if (CollectionUtils.isNotEmpty(sponsorList)) {
                throw new BizException("此电话号码已被注册！");
            }
            return responseDto.successData(sponsorService.insert(sponsor));

    }

    @PostMapping(value = "/updateById")
    public ResponseDto<Integer> updateSave(@RequestHeader("token") String token, Sponsor sponsor) {
        //权限验证
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        try {
            BizAssert.notNull(sponsor, BizCode.PARAM_NULL.getMessage());
            return responseDto.successData(sponsorService.updateById(sponsor));
        } catch (BizException e) {
            log.error("sponsor.updateById，原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("sponsor.updateById", e);
            return responseDto.failSys();
        }
    }

    @PostMapping("/query")
    public ResponseDto<List<Sponsor>> queryAllSponor(@RequestHeader("token") String token, SponsorQuery query) {
        ResponseDto<List<Sponsor>> responseDto = ResponseDto.successDto();
        try {
            BizAssert.notNull(query, BizCode.PARAM_NULL.getMessage());
            return responseDto.successData(sponsorService.findByCondition(query));
        } catch (BizException e) {
            log.error("sponsor.query，原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("sponsor.query", e);
            return responseDto.failSys();
        }
    }

    @PostMapping({"/data/real_time"})
    @ResponseBody
    public ResponseDto<MtaData> real_time() {
        ResponseDto<MtaData> responseDto = ResponseDto.successDto();
        try {
            MtaData data = sponsorService.findHistory();
            int count = activityUserService.findCountPass();
            data.setCount(count + "");
            return responseDto.successData(data);
        } catch (BizException e) {
            log.error("查询活动实时统计业务信息异常，原因: {}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("查询活动实时统计业务信息异常", e);
            return responseDto.failSys();
        }
    }

    @PostMapping({"/data/area"})
    @ResponseBody
    public ResponseDto<MtaBean> area(String start_time, String end_time) {
        ResponseDto<MtaBean> responseDto = ResponseDto.successDto();
        try {
            MtaBean data = sponsorService.findArea(start_time, end_time);
            return responseDto.successData(data);
        } catch (BizException e) {
            log.error("查询活动地域统计业务信息异常, 原因:{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("查询活动地域统计业务信息异常", e);
            return responseDto.failSys();
        }
    }

    @PostMapping({"/data/gender"})
    @ResponseBody
    public ResponseDto<MtaBean> gender() {
        ResponseDto<MtaBean> responseDto = ResponseDto.successDto();
        try {
            MtaBean data = this.userService.findGender();
            return responseDto.successData(data);
        } catch (BizException e) {
            log.error("查询性别统计业务信息异常, 原因:{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("查询性别统计业务信息异常", e);
            return responseDto.failSys();
        }
    }

    @PostMapping({"/data/age"})
    @ResponseBody
    public ResponseDto<HashMap<String, String>> age(String list) {
        ResponseDto<HashMap<String, String>> responseDto = ResponseDto.successDto();
        try {
            HashMap<String, String> data = this.userService.findAge(list);
            return responseDto.successData(data);
        } catch (BizException e) {
            log.error("查询年龄统计业务信息异常, 原因:{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("查询年龄统计业务信息异常", e);
            return responseDto.failSys();
        }
    }
}
