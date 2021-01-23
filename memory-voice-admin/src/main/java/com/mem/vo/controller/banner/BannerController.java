package com.mem.vo.controller.banner;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.mem.vo.business.base.model.po.Banner;
import com.mem.vo.business.base.model.po.BannerQuery;
import com.mem.vo.business.base.model.po.UserOperate;
import com.mem.vo.business.base.model.po.UserOperateQuery;
import com.mem.vo.business.base.service.BannerService;
import com.mem.vo.business.base.service.BasicAddressService;
import com.mem.vo.business.base.service.UserOperateService;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.model.vo.banner.BannerVo;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.BannerType;
import com.mem.vo.common.constant.UserOperateEnum;
import com.mem.vo.common.constant.VmOptionType;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.BeanCopyUtil;
import com.mem.vo.common.util.DateUtil;
import com.mem.vo.common.util.RedisUtils;
import com.mem.vo.config.annotations.CommonExHandler;
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
 * @date 2019/6/1 15:19
 */

@RestController
@RequestMapping("/banner")
@Slf4j
public class BannerController {

    @Resource
    private RedisUtils redisUtils;

    private static String REDIS_NAME = "banner_";

    @Resource
    private BannerService bannerService;

    @Resource
    private BasicAddressService basicAddressService;

    @Resource
    private TokenService tokenService;

    @Resource
    private UserOperateService userOperateService;

    @PostMapping("/queryBanner")
    public ResponseDto<List<BannerVo>> getList(BannerVo query) {

        ResponseDto<List<BannerVo>> responseDto = new ResponseDto<>();
        try {

            BannerQuery bannerQuery = new BannerQuery();
            bannerQuery.setType(BannerType.BANNER.getCode());
            bannerQuery.setOneAddress(query.getOneAddress());
            bannerQuery.setTwoAddress(query.getTwoAddress());
            bannerQuery.setIsDelete(0);
            bannerQuery.setEnable(1);
            bannerQuery.setAllPlace(0);
            List<Banner> banners = bannerService.findByCondition(bannerQuery);
            if (CollectionUtils.isEmpty(banners)) {
                bannerQuery.setAllPlace(1);
                bannerQuery.setOneAddress(null);
                bannerQuery.setTwoAddress(null);
                banners = bannerService.findByCondition(bannerQuery);

            }

            if (CollectionUtils.isEmpty(banners)) {
                return responseDto;
            }
            List<BannerVo> bannerVos = new ArrayList<>(banners.size());
            banners.forEach(item -> {
                BannerVo bannerVo = BeanCopyUtil.copyProperties(item, BannerVo.class);
                assembleAddress(bannerVo);

                bannerVos.add(bannerVo);
            });
            return responseDto.successData(bannerVos);
        } catch (BizException e) {

            log.error("查询banner图片异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("查询banner图片系统异常", e);
            return responseDto.failSys();

        }
    }


    @PostMapping("/queryOpeningPage")
    @CommonExHandler(key = "查询开屏页轮播图")
    public ResponseDto<Page<Banner>> queryOpeningPageByphone(Page page, BannerQuery query) {

        ResponseDto<Page<Banner>> responseDto = new ResponseDto<>();
        try {
            query.setIsDelete(0);
            Page<Banner> banners = bannerService.findPageByCondition(page, query);
            if (CollectionUtils.isNotEmpty(banners.getData())) {
                for (Banner banner : banners.getData()) {
                    assembleAddress(banner);
                }
            }
            return responseDto.successData(banners);
        } catch (Exception e) {
            log.debug("token未登录", e.getMessage());
            responseDto.setCode(3);
            return responseDto;
        }
    }


    @PostMapping("/queryOpeningPage/forwx")
    @CommonExHandler(key = "查询开屏页轮播图（微信小程序端）")
    public ResponseDto<JSONObject> queryOpeningPage(@RequestHeader("token") String token, Page page,
                                                    BannerQuery query) {

        JSONObject jsonObject = new JSONObject();
        ResponseDto<JSONObject> responseDto = new ResponseDto<>();
        query.setIsDelete(0);
        query.setType(BannerType.OPENING_PAGE.getCode());
        query.setEnable(1);
        Page<Banner> banners = bannerService.findPageByCondition(page, query);
        if (CollectionUtils.isNotEmpty(banners.getData())) {
            for (Banner banner : banners.getData()) {
                assembleAddress(banner);
            }
        }
        jsonObject.put("total", banners.getTotal());
        jsonObject.put("data", banners.getData());

        CommonLoginContext context = tokenService.getContextByken(token);
        UserOperateQuery userOperateQuery = new UserOperateQuery();
        userOperateQuery.setUserId(context.getUserId());
        userOperateQuery.setStartDate(DateUtil.getToday());
        userOperateQuery.setType(UserOperateEnum.LOGIN.getCode());
        List<UserOperate> operateList = userOperateService.findByCondition(userOperateQuery);
        if (CollectionUtils.isNotEmpty(operateList)) {
            jsonObject.put("show", 2);

        } else {
            jsonObject.put("show", 1);

        }
        return responseDto.successData(jsonObject);

    }

    @PostMapping("/deleteById")
    @CommonExHandler(key = "删除开屏页轮播图")
    public ResponseDto<Boolean> deleteById(@RequestHeader("token") String token, Long id) {
        ResponseDto<Boolean> responseDto = ResponseDto.successDto();
        bannerService.deleteById(id);
        return responseDto.successData(true);
    }


    @PostMapping("/addOrUpdate")
    public ResponseDto<Boolean> addOrUpdate(@RequestHeader("token") String token, BannerVo bannerVo,
                                            Integer optType) {
        ResponseDto<Boolean> responseDto = ResponseDto.successDto();
        Banner banner = BeanCopyUtil.copyProperties(bannerVo, Banner.class);
        if (VmOptionType.INSERT.getCode().equals(optType)) {
            bannerService.insert(banner);
        } else {
            BizAssert.notNull(banner.getId(), "id为空");
            bannerService.updateById(banner);
        }
        return responseDto.successData(true);


    }


    private void assembleAddress(BannerVo banner) {
        if (StringUtils.isNotBlank(banner.getOneAddress())) {
            banner.setOneAddressName(basicAddressService.findNameByCode(banner.getOneAddress()));
        }

        if (StringUtils.isNotBlank(banner.getTwoAddress())) {
            banner.setTwoAddressName(basicAddressService.findNameByCode(banner.getTwoAddress()));
        }
    }

    private void assembleAddress(Banner banner) {
        if (StringUtils.isNotBlank(banner.getOneAddress())) {
            banner.setOneAddressName(basicAddressService.findNameByCode(banner.getOneAddress()));
        }

        if (StringUtils.isNotBlank(banner.getTwoAddress())) {
            banner.setTwoAddressName(basicAddressService.findNameByCode(banner.getTwoAddress()));
        }
    }
}
