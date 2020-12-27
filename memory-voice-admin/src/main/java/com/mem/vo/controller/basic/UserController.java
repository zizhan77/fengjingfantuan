package com.mem.vo.controller.basic;

import com.mem.vo.business.base.model.po.User;
import com.mem.vo.business.base.model.po.UserOperate;
import com.mem.vo.business.base.model.po.UserOperateQuery;
import com.mem.vo.business.base.model.po.UserQuery;
import com.mem.vo.business.base.service.UserOperateService;
import com.mem.vo.business.base.service.UserService;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.SourceType;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.config.annotations.CommonExHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author litongwei
 * @description 用户管理
 * @date 2019/6/24 14:15
 */

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private UserOperateService userOperateService;

    @Resource
    private TokenService tokenService;

    @PostMapping("/queryUserList")
    public ResponseDto<Page<User>> queryUserList(@RequestHeader("token") String token,UserQuery userQuery, Page page) {
        ResponseDto<Page<User>> responseDto = new ResponseDto();

        userQuery.setSource(SourceType.WX_MINI.getCode());
        userQuery.setIsDelete(0);
        Page<User> resPage = userService.findPageByCondition(page,userQuery);
        BizAssert.notNull(resPage,"查询用户返回为空");
        responseDto.successData(page);
        return responseDto;
    }


    @PostMapping("/updateUserStatus")
    public ResponseDto<Boolean> updateUserStatus(@RequestHeader("token") String token, Long userId,Integer status) {
        BizAssert.notNull(userId,"用户 id为空，参数错误");
        BizAssert.notNull(status,"状态为空，参数错误");
        ResponseDto<Boolean> responseDto = new ResponseDto();
        User user = new User();
        user.setId(userId);
        user.setStatus(status);
        userService.updateById(user);
        tokenService.delteTokenByUserId(userId);
        responseDto.successData(true);
        return responseDto;
    }



    @PostMapping("/queryOptRecord")
    public ResponseDto<Page<UserOperate>> queryOptRecord(@RequestHeader("token") String token, Page page,UserOperateQuery userOperateQuery) {
        //BizAssert.notNull(userOperateQuery.getUserId(),"用户 id为空，参数错误");
        ResponseDto<Page<UserOperate>> responseDto = new ResponseDto();
        userOperateQuery.setIsDelete(0);
        if(userOperateQuery.getStartTime()!=null){
            userOperateQuery.setStartDate(new Date(userOperateQuery.getStartTime()));
        }

        if(userOperateQuery.getEndTime()!=null){
            userOperateQuery.setEndDate(new Date(userOperateQuery.getEndTime()));
        }

        Page<UserOperate> resPage = userOperateService.findPageByCondition(page,userOperateQuery);
        BizAssert.notNull(resPage,"查询用户操作记录返回为空");
        responseDto.successData(resPage);
        return responseDto;
    }

    @CommonExHandler(key = "查询当前用户ID")
    @PostMapping("/queryUserId")
    public ResponseDto<Long> queryByToken(@RequestHeader("token") String token) {
        CommonLoginContext context = tokenService.getContextByken(token);
        ResponseDto<Long> responseDto = ResponseDto.successDto();
        return responseDto.successData(context.getUserId());
    }
}
