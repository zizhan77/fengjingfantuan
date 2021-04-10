package com.mem.vo.controller.activity;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mem.vo.business.base.model.po.Activity;
import com.mem.vo.business.base.model.po.ActivityQuery;
import com.mem.vo.business.base.model.po.ActivityRuleUrl;
import com.mem.vo.business.base.model.po.Prize;
import com.mem.vo.business.base.model.po.Sponsor;
import com.mem.vo.business.base.model.vo.ActivityVO;
import com.mem.vo.business.base.service.ActivityRuleUrlService;
import com.mem.vo.business.base.service.ActivityService;
import com.mem.vo.business.base.service.PerformanceService;
import com.mem.vo.business.base.service.SponsorService;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.PageBean;
import com.mem.vo.common.dto.PageBeanCopy;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.BeanCopyUtil;
import com.mem.vo.common.util.RedisUtils;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import com.mem.vo.config.annotations.CommonExHandler;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 活动信息查询
 *
 * @author lvxiao
 * @version V1.0
 * @date 2019-06-16 15:15
 */
@RestController
@RequestMapping("/activity")
@Slf4j
public class ActivityController {

    @Resource
    private ActivityRuleUrlService activityRuleUrlService;

    @Resource
    private SponsorService sponsorService;

    @Resource
    private PerformanceService performanceService;

    @Resource
    private ActivityService activityService;

    @Resource
    private TokenService tokenService;

    @Resource
    private RedisUtils redisUtils;

    private static String REDIS_NAME = "activity_";

    @PostMapping({"/phone/queryActivityByUser"})
    public ResponseDto<PageBean<ActivityVO>> queryActivityByUser(@RequestHeader("token") String token, @RequestParam(required = true) Integer pageNo, @RequestParam(required = true) Integer pageSize, @RequestParam(required = true) Integer flag) {
        ResponseDto<PageBean<ActivityVO>> responseDto = ResponseDto.successDto();
        CommonLoginContext context = tokenService.getContextByken(token);
        try {
            if (context.getUserId() == null) {
                responseDto.setCode(Integer.valueOf(3));
                return responseDto;
            }
        } catch (Exception e) {
            log.debug("queryActivityByUser有问题", e.getMessage());
                    responseDto.setCode(Integer.valueOf(1));
            return responseDto;
        }
        PageBean<ActivityVO> byPage = activityService.queryActivityByUser(pageNo, pageSize, flag, context.getUserId());
        return responseDto.successData(byPage);
    }

    @PostMapping({"/phone/queryActivityDetail"})
    public ResponseDto<ActivityVO> queryDetailByPhone(Integer id) {
        ResponseDto<ActivityVO> responseDto = ResponseDto.successDto();
        try {
            BizAssert.notNull(id, BizCode.PARAM_NULL.getMessage());
            ActivityVO byPage = activityService.queryDetailByPhone(id);
            String s1 = JSON.toJSONString(byPage);
            return responseDto.successData(byPage);
        } catch (BizException e) {
            log.error("删除活动异常，参数:{},原因:{}", id, e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("删除活动异常，参数:{}", id, e);
            return responseDto.failSys();
        }
    }

    @PostMapping({"/phone/queryAllByPhone"})
    public ResponseDto<PageBeanCopy<ActivityVO>> queryAllByPhone(@RequestParam(required = true) Integer pageNo, @RequestParam(required = true) Integer pageSize) {
        ResponseDto<PageBeanCopy<ActivityVO>> responseDto = ResponseDto.successDto();
        try {
            PageBean<ActivityVO> byPage = activityService.findByPageToPhone(pageNo, pageSize);
            String s1 = JSON.toJSONString(byPage);
            PageBeanCopy pageBeanCopy = JSONObject.parseObject(s1, PageBeanCopy.class);
            return responseDto.successData(pageBeanCopy);
        } catch (Exception e) {
            log.debug("token未登录", e.getMessage());
                    responseDto.setCode(Integer.valueOf(3));
            return responseDto;
        }
    }

    @PostMapping({"/getActivity"})
    public ResponseDto<PageBeanCopy<ActivityVO>> getActivity(@RequestParam(required = true) Integer pageNo, @RequestParam(required = true) Integer pageSize, String name) {
        ResponseDto<PageBeanCopy<ActivityVO>> responseDto = ResponseDto.successDto();
        try {
            PageBean<ActivityVO> byPage = activityService.getActivity(pageNo, pageSize, name);
            String s1 = JSON.toJSONString(byPage);
            PageBeanCopy pageBeanCopy = JSONObject.parseObject(s1, PageBeanCopy.class);
            return responseDto.successData(pageBeanCopy);
        } catch (Exception e) {
            log.debug("token未登录", e.getMessage());
            responseDto.setCode(Integer.valueOf(3));
            return responseDto;
        }
    }

    @PostMapping("/queryAll")
    public ResponseDto<Page<ActivityVO>> queryAll(ActivityQuery activityQuery, Page page) {
        //权限验证
        ResponseDto<Page<ActivityVO>> responseDto = ResponseDto.successDto();
        List<Sponsor> sponsors = new ArrayList<>();
        try {
            Page<Activity> byPage = activityService.findByPage(activityQuery, page);
            List<ActivityVO> activityVOS = new ArrayList<>();
            ActivityVO activityVO = new ActivityVO();
            for (Activity ac : byPage.getData()) {
                sponsors.clear();
                ActivityVO activityVO1 = BeanCopyUtil.copyProperties(ac, activityVO.getClass());
                if (ac.getSponsorId() != null && !"".equals(ac.getSponsorId())) {
                    String[] split = ac.getSponsorId().split("~");
                    for (String s : split) {
                        Sponsor sponsor = sponsorService.findById(Long.valueOf(s));
                        sponsors.add(sponsor);
                    }
                    activityVO1.setSponsors(sponsors);
                }
                activityVOS.add(activityVO1);
            }
            Page<ActivityVO> activityVOPage = new Page();
            activityVOPage.setData(activityVOS);
            activityVOPage.setPage(byPage.getPage());
            activityVOPage.setTotal(byPage.getTotal());
            activityVOPage.setLimit(byPage.getLimit());
            return responseDto.successData(activityVOPage);
//            return responseDto.successData(activityService.findByConditionAvailable(new ActivityQuery()));
        } catch (BizException e) {

            log.error("查询活动异常，原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("查询活动异常", e);
            return responseDto.failSys();
        }
    }

    @PostMapping("/queryAvailableList")
    public ResponseDto<List<Activity>> queryAllActivity(@RequestHeader("token") String token, ActivityQuery query) {
        //权限验证
        ResponseDto<List<Activity>> responseDto = ResponseDto.successDto();
        try {
            //不校验是否为空，返回所有
            return responseDto.successData(activityService.findByConditionAvailable(query));
        } catch (BizException e) {

            log.error("activity.queryAvailableList异常，原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("activity.queryAvailableList异常", e);
            return responseDto.failSys();
        }

    }

    @PostMapping("/add")
    public ResponseDto<Integer> addActivity(@RequestHeader("token") String token, Activity activity) {

        //权限验证
        ResponseDto<Integer> responseDto = ResponseDto.successDto();

        try {
            BizAssert.notNull(activity, BizCode.PARAM_NULL.getMessage());
            return responseDto.successData(activityService.insert(activity));
        } catch (BizException e) {

            log.error("创建活动异常，参数:{},原因：{}", activity.getId(), e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("创建活动异常，参数:{}", activity.getId(), e);
            return responseDto.failSys();
        }
    }

    @PostMapping("/updateById")
    public ResponseDto<Integer> updateById(@RequestHeader("token") String token, Activity activity) {

        //权限验证
        ResponseDto<Integer> responseDto = ResponseDto.successDto();

        try {
            BizAssert.notNull(activity.getId(), BizCode.PARAM_NULL.getMessage());
            return responseDto.successData(activityService.updateById(activity));
        } catch (BizException e) {

            log.error("更新活动异常，参数:{},原因：{}", activity.getId(), e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("更新活动异常，参数:{}", activity.getId(), e);
            return responseDto.failSys();
        }
    }

    @PostMapping("/deleteById")
    public ResponseDto<Integer> deleteById(@RequestHeader("token") String token, String id) {
        //权限验证
        ResponseDto<Integer> responseDto = ResponseDto.successDto();

        try {
            BizAssert.notNull(id, BizCode.PARAM_NULL.getMessage());
            return responseDto.successData(activityService.deleteById(Long.valueOf(id)));
        } catch (BizException e) {

            log.error("删除活动异常，参数:{},原因：{}", id, e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("删除活动异常，参数:{}", id, e);
            return responseDto.failSys();
        }
    }

    @PostMapping({"ruleUrl/edit"})
    public ResponseDto<ActivityRuleUrl> updateRuleUrlById(@RequestHeader("token") String token, ActivityRuleUrl activity) {
        ResponseDto<ActivityRuleUrl> responseDto = ResponseDto.successDto();
        try {
            return responseDto.successData(activityRuleUrlService.updateById(activity));
        } catch (BizException e) {
            log.error("编辑活动规则图片异常，参数:{},原因:{}", activity.getId(), e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("编辑活动规则图片异常, 参数:{}", activity.getId(), e);
            return responseDto.failSys();
        }
    }

    @PostMapping({"ruleUrl/query"})
    public ResponseDto<List<ActivityRuleUrl>> queryRuleUrlById() {
        ResponseDto<List<ActivityRuleUrl>> responseDto = ResponseDto.successDto();
        try {
            return responseDto.successData(activityRuleUrlService.query());
        } catch (BizException e) {
            log.error("查询活动规则图片异常，参数:{},原因:{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("查询活动规则图片异常, 参数:{}", e);
            return responseDto.failSys();
        }
    }

    @PostMapping({"prize/query"})
    public ResponseDto<Prize> queryPrizeById(@RequestHeader("token") String token, String activityId) {
        ResponseDto<Prize> responseDto = ResponseDto.successDto();
        try {
            return responseDto.successData(activityService.queryPrizeById(activityId));
        } catch (BizException e) {
            log.error("查询活动门票奖品异常, 参数:{},原因:{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("查询活动门票奖品异常, 参数:{}", e);
            return responseDto.failSys();
        }
    }

    @PostMapping({"pc/queryAll"})
    public ResponseDto<Page<Activity>> pcQueryAll(ActivityQuery activityQuery, Page page) {
        ResponseDto<Page<Activity>> responseDto = ResponseDto.successDto();
        try {
            Page<Activity> byPage = activityService.pcFindByPage(activityQuery, page);
            return responseDto.successData(byPage);
        } catch (BizException e) {
            log.error("查询活动异常, 原因: {}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("查询活动异常", e);
            return responseDto.failSys();
        }
    }

//    /**
//     * 骰子接口，根据骰子的点数返回对应活动，因为活动只有三个写死，所以目前返回的活动就是固定的
//     *
//     * @param token 用户token
//     * @return 返回活动信息
//     */
//    @CommonExHandler(key = "返回筛子对应活动")
//    @PostMapping("/queryByPoints")
//    public ResponseDto<DiceVO> queryByPoints(@RequestHeader("token") String token) {
//        //权限验证
//        ResponseDto<DiceVO> responseDto = ResponseDto.successDto();
//        int points = (int) (1 + Math.random() * 6);
//        DiceVO diceVO = DiceVO.builder().id(points).name(Dice.diceMap.get(points)).build();
//        return responseDto.successData(diceVO);
//    }
    @PostMapping({"pc/queryGetOne"})
    public ResponseDto<Page<ActivityVO>> queryGetOne(ActivityQuery activityQuery, Page page) {
        ResponseDto<Page<ActivityVO>> responseDto = ResponseDto.successDto();
        List<Sponsor> sponsors = new ArrayList<>();
        try {
            Page<Activity> byPage = activityService.pcFindByPage(activityQuery, page);
            List<ActivityVO> activityVOS = new ArrayList<>();
            ActivityVO activityVO = new ActivityVO();
            for (Activity ac : byPage.getData()) {
                sponsors.clear();
                ActivityVO activityVO1 = BeanCopyUtil.copyProperties(ac, activityVO.getClass());
                if (ac.getSponsorId() != null && !"".equals(ac.getSponsorId())) {
                    String[] split = ac.getSponsorId().split("~");
                    for (String s : split) {
                        Sponsor sponsor = sponsorService.findById(Long.valueOf(s));
                        sponsors.add(sponsor);
                    }
                    activityVO1.setSponsors(sponsors);
                }
                activityVOS.add(activityVO1);
            }
            Page<ActivityVO> activityVOPage = new Page();
            activityVOPage.setData(activityVOS);
            activityVOPage.setPage(byPage.getPage());
            activityVOPage.setTotal(byPage.getTotal());
            activityVOPage.setLimit(byPage.getLimit());
            return responseDto.successData(activityVOPage);
        } catch (BizException e) {
            log.error("查询活动异常, 原因: {}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("查询活动异常", e);
            return responseDto.failSys();
        }
    }
}
