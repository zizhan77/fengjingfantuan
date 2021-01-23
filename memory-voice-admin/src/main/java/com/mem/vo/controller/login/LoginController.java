package com.mem.vo.controller.login;

import com.alibaba.fastjson.JSONObject;
import com.mem.vo.business.base.model.po.*;
import com.mem.vo.business.base.service.*;
import com.mem.vo.business.biz.model.dto.*;
import com.mem.vo.business.biz.service.login.OrganizerLoginService;
import com.mem.vo.business.biz.service.login.PcLoginService;
import com.mem.vo.business.biz.service.login.SmsLoginService;
import com.mem.vo.business.biz.service.login.WxLoginService;
import com.mem.vo.business.biz.service.sms.SmsService;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.*;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.JsonUtil;
import com.mem.vo.common.util.ValidateUtil;
import com.mem.vo.config.annotations.CommonExHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author litongwei
 * @description: 小程序&pc 登录
 * @date 2019/5/25 9:11
 */

@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Autowired
    private SponsorService sponsorService;

    @Autowired
    private OrganizerService organizerService;

    @Autowired
    private WxLoginService wxLoginService;

    @Autowired
    private PcLoginService<User> pcLoginService;

    @Autowired
    private PcLoginService<Sponsor> sponsorLoginService;

    @Resource
    private UserService userService;

    @Resource
    private OrganizerLoginService organizerLoginService;

    @Resource
    private TokenService tokenService;

    @Resource
    private AuthorityRoleMenuRelService authorityRoleMenuRelService;

    @Resource
    private AuthorityRoleService authorityRoleService;

    @Resource
    private AuthorityMenuService authorityMenuService;

    @Resource
    private SmsService smsService;

    @Resource
    private SmsLoginService smsLoginService;

    @Resource
    private UserOperateService userOperateService;

    @Resource
    private RewardService rewardService;

    @PostMapping({"/minoPro/getUser"})
    public ResponseDto<List<String>> miniProGetTokenforUser(String jscode) {
        ResponseDto<List<String>> responseDto = ResponseDto.successDto();
        try {
            BizAssert.hasText(jscode, BizCode.PARAM_NULL.getMessage());
            WxRpcContext wxRpcContext = wxLoginService.getRpcContext(jscode);
            BizAssert.isBlank(wxRpcContext.getErrmsg(), wxRpcContext.getErrmsg());
            BizAssert.isBlank(wxRpcContext.getErrcode(), wxRpcContext.getErrcode());
            UserQuery userQuery = new UserQuery();
            userQuery.setSource(SourceType.WX_MINI.getCode());
            userQuery.setBizCode(wxRpcContext.getOpenid());
            List<String> list = new ArrayList<>();
            String token = "";
            List<User> users = userService.findByCondition(userQuery);
            if (CollectionUtils.isEmpty(users)) {
                list.add("1");
            } else {
                BizAssert.notNull(users.get(0).getStatus(), "用户状态不存在");
                BizAssert.notNull(users.get(0).getIsDelete(), "用户状态不存在");
                if (users.get(0).getStatus().equals(EnableStatus.ON.getCode()) && users.get(0).getIsDelete().equals(Integer.valueOf(0))) {
                    User userInfo = users.get(0);
                    list.add("0");
                    token = wxLoginService.getToken(wxRpcContext, false, userInfo.getId());
                    list.add(token);
                } else {
                    throw new BizException("此用户已经被锁定或者被删除");
                }
            }
            return responseDto.successData(list);
        } catch (Exception e) {
            log.error("小程序登录业务异常, 参数: {},原因: {}", jscode, e.getMessage());
            return responseDto.failData(e.getMessage());
        }
    }

    @PostMapping("/minoPro/getToken")
    public ResponseDto<List<String>> miniProGetToken(String jscode) {

        ResponseDto<List<String>> responseDto = ResponseDto.successDto();
        try {
            User userInfo;
            BizAssert.hasText(jscode, BizCode.PARAM_NULL.getMessage());

            //登录验证
            WxRpcContext wxRpcContext = wxLoginService.getRpcContext(jscode);
            BizAssert.isBlank(wxRpcContext.getErrmsg(), wxRpcContext.getErrmsg());
            BizAssert.isBlank(wxRpcContext.getErrcode(), wxRpcContext.getErrcode());
            UserQuery userQuery = new UserQuery();
            userQuery.setSource(SourceType.WX_MINI.getCode());
            userQuery.setBizCode(wxRpcContext.getOpenid());
            List<String> list = new ArrayList<>();
            String token = "";
            //根据openid 分配 token
            List<User> users = userService.findByCondition(userQuery);
            String flag = "";
            if (CollectionUtils.isEmpty(users)) {
                User user = new User();
                user.setSource(SourceType.WX_MINI.getCode());
                user.setSourceName(SourceType.WX_MINI.getDescription());
                user.setBizCode(wxRpcContext.getOpenid());
                user.setCreateUser("system");
                user.setStatus(UserStatus.ENABLE.getCode());
                Integer def = rewardService.getUserDefaultIntegral();
                if (def != null) {
                    user.setIntegral(def);
                }
                user.setIsAuthorize(0);
                userService.insert(user);
                flag = "1";
                userInfo = user;

            } else {
                BizAssert.notNull(users.get(0).getStatus(), "用户状态不存在");
                BizAssert.notNull(users.get(0).getIsDelete(), "用户状态不存在");
                if (users.get(0).getStatus().equals(EnableStatus.ON.getCode()) && users.get(0).getIsDelete().equals(0)) {
                    userInfo = users.get(0);
                    flag = "0";
                } else {
                    throw new BizException("此用户已经被锁定或者被删除");
                }
            }

            if (userInfo.getIsAuthorize() == 1) {
                token = wxLoginService.getToken(wxRpcContext, true, userInfo.getId());
                UserOperate userOperate = new UserOperate();
                userOperate.setUserId(userInfo.getId());
                userOperate.setPhoneNumber(userInfo.getPhoneNumber());
                userOperate.setType(UserOperateEnum.LOGIN.getCode());
                try {
                    userOperateService.insert(userOperate);
                } catch (Exception e) {
                    log.error("登录写操作日志失败：用户id :{}", userInfo.getId(), e);
                }

            } else {

                token = wxLoginService.getToken(wxRpcContext, false, userInfo.getId());
            }
            list.add(token);
            if (users != null && users.size() > 0 && users.get(0) != null) {
                Long id = users.get(0).getId();
                if (id != null) {
                    list.add(id + "");
                }
            }
            list.add(flag);
            return responseDto.successData(list);

        } catch (BizException e) {

            log.error("小程序登录业务异常，参数:{},原因：{}", jscode, e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("小程序登录系统异常，参数:{}", jscode, e);
            return responseDto.failSys();

        }

    }

    @PostMapping("/pc/getToken")
    public ResponseDto<String> pcGetToken(PcLoginRequest pcLoginRequest) {
        ResponseDto<String> responseDto = ResponseDto.successDto();

        try {
            //参数校验
            String validateStr = ValidateUtil.getValidateStr(pcLoginRequest);
            BizAssert.isBlank(validateStr, validateStr);
            User user = pcLoginService.checkPasswd(pcLoginRequest);
            BizAssert.notNull(user.getStatus(), "用户状态不存在");
            BizAssert.isTrue(EnableStatus.ON.getCode().equals(user.getStatus()), "用户被冻结，请联系运营");
            //获取 token
            String token = pcLoginService.getToken(pcLoginRequest, user);
            return responseDto.successData(token);

        } catch (BizException e) {

            log.error("pc登录业务异常，参数:{},原因：{}", JsonUtil.toJson(pcLoginRequest), e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("pc登录系统异常，参数:{}", JsonUtil.toJson(pcLoginRequest), e);
            return responseDto.failSys();

        }
    }

    @PostMapping("/pc/getInfo")
    public ResponseDto<JSONObject> getInfo(@RequestHeader String token) {
        ResponseDto<JSONObject> responseDto = ResponseDto.successDto();

        try {
            JSONObject jsonObject = new JSONObject();
            CommonLoginContext context = tokenService.getContextByken(token);
            BizAssert.notNull(context, "查询token信息为空");
            BizAssert.notNull(context.getUserId(), "token 保存  userid 为空");
//            User user = userService.findById(Long.valueOf(context.getUserId()));
//            jsonObject.put("user", user);
//
//            AuthorityRole role = authorityRoleService.findById(Long.valueOf(user.getRole()));
//            jsonObject.put("role", role);
//
//            AuthorityRoleMenuRelQuery query = new AuthorityRoleMenuRelQuery();
//            query.setRoleId(Long.valueOf(user.getRole()));
//            List<AuthorityRoleMenuRel> relList = authorityRoleMenuRelService.findByCondition(query);
//            List<AuthorityMenu> menuList = new ArrayList<>();
            if (context.getUser() != null) {
                User user = userService.findById(Long.valueOf(context.getUserId()));
                jsonObject.put("user", user);

                AuthorityRole role = authorityRoleService.findById(Long.valueOf(user.getRole()));
                jsonObject.put("role", role);

                AuthorityRoleMenuRelQuery query = new AuthorityRoleMenuRelQuery();
                query.setRoleId(Long.valueOf(user.getRole()));

                List<AuthorityRoleMenuRel> relList = authorityRoleMenuRelService.findByCondition(query);
                List<AuthorityMenu> menuList = new ArrayList<>();

                if (CollectionUtils.isNotEmpty(relList)) {
                    for (AuthorityRoleMenuRel authorityRoleMenuRel : relList) {

                        AuthorityMenu menu = authorityMenuService.findById(Long.valueOf(authorityRoleMenuRel.getMenuId()));
                        menuList.add(menu);
                    }
                }
                jsonObject.put("menuList", menuList);

            }

            if (context.getOrganizer() != null) {
                Organizer byId = organizerService.findById(context.getUserId());
                jsonObject.put("user", byId);
            }

            if (context.getSponsor() != null) {
                Sponsor byId = sponsorService.findById(context.getUserId());
                jsonObject.put("user", byId);
            }

            return responseDto.successData(jsonObject);

        } catch (BizException e) {

            log.error("业务异常，参数:{},原因：{}", token, e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("pc登录系统异常，参数:{}", token, e);
            return responseDto.failSys();

        }
    }

    @CommonExHandler(key = "验票员端短信接收验证码")
    @PostMapping("/sms/getVerificationCode")
    public ResponseDto<Boolean> getVerificationCode(@NotBlank(message = "电话号码不能为空！") String phoneNumber) {
        ResponseDto<Boolean> responseDto = ResponseDto.successDto();
        UserQuery query = new UserQuery();
        query.setPhoneNumber(phoneNumber);
        query.setSource(SourceType.CHECKMAN.getCode());
        query.setIsDelete(0);
        List<User> userList = userService.findByCondition(query);
        BizAssert.notEmpty(userList, "请联系管理员！当前用户不是赞助商兑换端用户，或者未在我方小程序注册！");
        BizAssert.notNull(userList.get(0).getStatus(), "用户状态不存在");
        BizAssert.isTrue(EnableStatus.ON.getCode().equals(userList.get(0).getStatus()), "用户被冻结，请联系运营");
        BizAssert.notNull(userList.get(0).getIsDelete(), "用户不存在");
        return responseDto.successData(smsService.sendSms(StringUtils.trim(phoneNumber)));
    }

    @CommonExHandler(key = "验票员短信登录")
    @PostMapping("/sms/getToken")
    public ResponseDto<String> smsGetToken(SmsLoginRequest request) {
        ResponseDto<String> responseDto = ResponseDto.successDto();
        if (smsService.checkSms(request.getPhoneNumber(), request.getVerificationCode())) {
            UserQuery query = new UserQuery();
            query.setPhoneNumber(request.getPhoneNumber());
            query.setSource(SourceType.CHECKMAN.getCode());
            query.setIsDelete(0);
            List<User> userList = userService.findByCondition(query);
            BizAssert.notEmpty(userList, "当前用户不是验票员，请联系管理员");

            BizAssert.notNull(userList.get(0).getStatus(), "用户状态不存在");
            BizAssert.isTrue(EnableStatus.ON.getCode().equals(userList.get(0).getStatus()), "用户被冻结，请联系运营");

            /*if (CollectionUtils.isEmpty(userList)) {
                User user = new User();
                user.setPhoneNumber(request.getPhoneNumber());
                user.setBizCode(request.getPhoneNumber());
                user.setSourceName(request.getSourceName());
                userService.insert(user);
                return responseDto.successData(smsLoginService.getToken(user));
            }*/
            //感觉这里有问题，电话号码怎么能查出来多个用户
            return responseDto.successData(smsLoginService.getToken(userList.get(0)));
        }
        throw new BizException("您输入的验证码有误，请重新输入！");
    }

    @CommonExHandler(key = "赞助商短信接收验证码")
    @PostMapping("/sponsorExchange/getVerificationCode")
    public ResponseDto<Boolean> getSponsorExchangeVerificationCode(@NotBlank(message = "电话号码不能为空！") String phoneNumber) {
        ResponseDto<Boolean> responseDto = ResponseDto.successDto();
        UserQuery query = new UserQuery();
        query.setPhoneNumber(phoneNumber);
        query.setSource(SourceType.SPONSOR_EXCHANGE.getCode());
        query.setIsDelete(0);
        List<User> userList = userService.findByCondition(query);
        BizAssert.notEmpty(userList, "请联系管理员！当前用户不是赞助商兑换端用户，或者未在我方小程序注册！");
        BizAssert.notNull(userList.get(0).getStatus(), "用户状态不存在");
        BizAssert.isTrue(EnableStatus.ON.getCode().equals(userList.get(0).getStatus()), "用户被冻结，请联系运营");
        return responseDto.successData(smsService.sendSms(StringUtils.trim(phoneNumber)));
    }

    @CommonExHandler(key = "赞助商兑换端短信登录")
    @PostMapping("/sponsorExchange/getToken")
    public ResponseDto<String> sponsorExchangeGetToken(SmsLoginRequest request) {
        ResponseDto<String> responseDto = ResponseDto.successDto();
        if (smsService.checkSms(request.getPhoneNumber(), request.getVerificationCode())) {
            UserQuery query = new UserQuery();
            query.setPhoneNumber(request.getPhoneNumber());
            query.setSource(SourceType.SPONSOR_EXCHANGE.getCode());
            query.setIsDelete(0);
            List<User> userList = userService.findByCondition(query);
            BizAssert.notEmpty(userList, "请联系管理员！当前用户不是赞助商兑换端用户，或者未在我方小程序注册！");
            BizAssert.notNull(userList.get(0).getStatus(), "用户状态不存在");
            BizAssert.isTrue(EnableStatus.ON.getCode().equals(userList.get(0).getStatus()), "用户被冻结，请联系运营");
            return responseDto.successData(smsLoginService.getTokenFifteenDay(userList.get(0)));
        }
        throw new BizException("您输入的验证码有误，请重新输入！");
    }

    @CommonExHandler(key = "赞助商登录")
    @PostMapping("/sponsor/getToken")
    public ResponseDto<String> sponsorToken(PcLoginRequest pcLoginRequest) {
        ResponseDto<String> responseDto = ResponseDto.successDto();
        //参数校验
        String validateStr = ValidateUtil.getValidateStr(pcLoginRequest);
        BizAssert.isBlank(validateStr, validateStr);
        Sponsor sponsor = sponsorLoginService.checkPasswd(pcLoginRequest);
        BizAssert.notNull(sponsor, "用户状态不存在");
        BizAssert.notNull(sponsor.getStatus(), "用户状态不存在");
        BizAssert.isTrue(EnableStatus.ON.getCode().equals(sponsor.getStatus()), "用户被冻结，请联系运营");
        if (!sponsor.getIsDelete().equals(Integer.valueOf(0))) {
            throw new BizException("用户已经被删除");
        }
        String token = sponsorLoginService.getToken(pcLoginRequest, sponsor);
        System.out.println("contro" + token);
        return responseDto.successData(token);
    }

    @CommonExHandler(key = "主办方登录")
    @PostMapping("/organizer/getToken")
    public ResponseDto<String> organizerToken(OrganizerLoginRequest organizerLoginRequest) {
        ResponseDto<String> responseDto = ResponseDto.successDto();
        try {

            //参数校验
            String validateStr = ValidateUtil.getValidateStr(organizerLoginRequest);
            BizAssert.isBlank(validateStr, validateStr);
            Organizer organizer = organizerLoginService.checkPasswd(organizerLoginRequest);
            BizAssert.notNull(organizer, "用户状态不存在");
            BizAssert.notNull(Integer.valueOf(organizer.getStatus()), "用户状态不存在");
            BizAssert.isTrue(EnableStatus.ON.getCode().equals(Integer.valueOf(organizer.getStatus())), "用户被冻结，请联系运营");
            BizAssert.isTrue((organizer.getIsDelete() == 0), "用户已被删除");
            String token = organizerLoginService.getToken(organizerLoginRequest, organizer);
            return responseDto.successData(token);

        } catch (BizException e) {

            log.error("主办方登录业务异常，参数:{},原因：{}", JsonUtil.toJson(organizerLoginRequest), e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("主办方登录业务异常，参数:{}", JsonUtil.toJson(organizerLoginRequest), e);
            return responseDto.failSys();

        }


    }

//    @PostMapping("/organizer/getInfo")
//    public ResponseDto<JSONObject> getOrInfo(@RequestHeader String token) {
//        ResponseDto<JSONObject> responseDto = ResponseDto.successDto();
//        try {
//            JSONObject jsonObject = new JSONObject();
//            CommonLoginContext context = tokenService.getContextByken(token);
//            BizAssert.notNull(context, "查询token信息为空");
//            BizAssert.notNull(context.getUserId(), "token 保存  userid 为空");
//            Organizer organizer = organizerService.findById(Long.valueOf(context.getUserId()));
//            jsonObject.put("user", organizer);
//
//         /*  // AuthorityRole role = authorityRoleService.findById(Long.valueOf(user.getRole()));
//            //jsonObject.put("role", role);
//
//            AuthorityRoleMenuRelQuery query = new AuthorityRoleMenuRelQuery();
//        //    query.setRoleId(Long.valueOf(user.getRole()));
//            List<AuthorityRoleMenuRel> relList = authorityRoleMenuRelService.findByCondition(query);
//            List<AuthorityMenu> menuList = new ArrayList<>();
//            if (CollectionUtils.isNotEmpty(relList)) {
//                for (AuthorityRoleMenuRel authorityRoleMenuRel : relList) {
//
//                    AuthorityMenu menu = authorityMenuService.findById(Long.valueOf(authorityRoleMenuRel.getMenuId()));
//                    menuList.add(menu);
//                }
//            }
//            jsonObject.put("menuList", menuList);*/
//
//            return responseDto.successData(jsonObject);
//
//        } catch (BizException e) {
//
//            log.error("业务异常，参数:{},原因：{}", token, e.getMessage());
//            return responseDto.failData(e.getMessage());
//        } catch (Exception e) {
//
//            log.error("主板方登录系统异常，参数:{}", token, e);
//            return responseDto.failSys();
//
//        }
//    }


    @CommonExHandler(key = "主办方登出")
    @PostMapping("/organizer/loginOut")
    public ResponseDto<String> organizerToken(@RequestHeader("token") String token) {

        ResponseDto<String> responseDto = ResponseDto.successDto();
        try {

            BizAssert.notNull(token, BizCode.PARAM_NULL.getMessage());
            boolean b = organizerLoginService.loginOut(token);
            if (b) {
                responseDto.successData(token);
            } else {
                responseDto.failData(token);
            }
            return responseDto;

        } catch (BizException e) {

            log.error("主办方登出业务异常，参数:{},原因：{}", JsonUtil.toJson(token), e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("主办方登出业务异常，参数:{}", JsonUtil.toJson(token), e);
            return responseDto.failSys();

        }
    }
}
