package com.mem.vo.controller.basic;

import com.mem.vo.business.base.model.po.User;
import com.mem.vo.business.base.model.po.UserQuery;
import com.mem.vo.business.base.service.UserService;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.model.dto.WxJsTokenResponse;
import com.mem.vo.business.biz.service.login.WxLoginService;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.SourceType;
import com.mem.vo.common.constant.VmOptionType;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.config.annotations.CommonExHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author litongwei
 * @description  验票员管理
 * @date 2019/6/24 14:16
 */

@RestController
@RequestMapping("/checkman")
@Slf4j
public class CheckManController {

    @Resource
    private UserService userService;

    @Resource
    private TokenService tokenService;

    @Resource
    private WxLoginService wxLoginService;

    @PostMapping("/queryCheckManList")
    public ResponseDto<Page<User>> queryCheckManList(@RequestHeader("token") String token, UserQuery query, Page page) {
        ResponseDto<Page<User>> responseDto = new ResponseDto();

        query.setSource(SourceType.CHECKMAN.getCode());
        query.setIsDelete(0);
        Page<User> resPage = userService.findPageByCondition(page, query);
        BizAssert.notNull(resPage, "查询验票员返回为空");
        responseDto.successData(page);
        return responseDto;
    }

    @PostMapping("/addOrUpdateCheckMan")
    @CommonExHandler(key = "新增/修改验票员信息")
    public ResponseDto<Long> addOrUpdateCheckMan(@RequestHeader("token") String token, User user, Integer optType) {
        BizAssert.notNull(optType, "操作类型为空，参数异常");
        ResponseDto<Long> responseDto = new ResponseDto();

        UserQuery userQuery = new UserQuery();
        userQuery.setIsDelete(0);
        userQuery.setPhoneNumber(user.getPhoneNumber());
        userQuery.setSource(SourceType.CHECKMAN.getCode());


        if (optType.equals(VmOptionType.INSERT.getCode())) {
            List<User> userList = userService.findByCondition(userQuery);
            if (CollectionUtils.isNotEmpty(userList)) {
                throw new BizException("用户已存在，无法新增");
            }

            user.setSource(SourceType.CHECKMAN.getCode());
            user.setSourceName(SourceType.CHECKMAN.getDescription());
            user.setBizCode(user.getPhoneNumber());
            user.setIsDelete(0);
            int count = userService.insert(user);
            responseDto.successData(user.getId());
        } else {
            List<User> userList = userService.findByCondition(userQuery);
            if (CollectionUtils.isNotEmpty(userList) && ! user.getId().equals(userList.get(0).getId())) {
                throw new BizException("相同手机号的用户已存在，无法修改");
            }

            int count = userService.updateById(user);
            responseDto.successData(user.getId());
            //删除验票员 token
            tokenService.delteTokenByUserId(user.getId());
        }

        return responseDto;
    }


    @PostMapping("/queryInfo")
    @CommonExHandler(key = "查询验票员信息")
    public ResponseDto<User> queryInfo(@RequestHeader("token") String token) {
        CommonLoginContext context = tokenService.getContextByken(token);
        return new ResponseDto().successData(context.getUser());
    }

    @CommonExHandler(key = "获取签名 ")
    @PostMapping("/wx/getSign")
    public ResponseDto<WxJsTokenResponse> wxGetSign(String url) {

        WxJsTokenResponse response = wxLoginService.getWxSign(url);
        return new ResponseDto().successData(response);

    }



}
