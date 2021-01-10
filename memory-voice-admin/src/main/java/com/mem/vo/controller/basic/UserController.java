package com.mem.vo.controller.basic;

import com.mem.vo.business.base.model.po.Integral;
import com.mem.vo.business.base.model.po.User;
import com.mem.vo.business.base.model.po.UserOperate;
import com.mem.vo.business.base.model.po.UserOperateQuery;
import com.mem.vo.business.base.model.po.UserQuery;
import com.mem.vo.business.base.model.vo.UserUpdatelottery;
import com.mem.vo.business.base.service.UserOperateService;
import com.mem.vo.business.base.service.UserService;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.SourceType;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.PageBean;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.config.annotations.CommonExHandler;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


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

    @PostMapping({"/pc/Updatelottery"})
    public ResponseDto<Boolean> Updatelottery(UserUpdatelottery user) {
        ResponseDto<Boolean> responseDto = new ResponseDto();
        BizAssert.notNull(user.getUsercode(), "usercode为null, 参数错误");
        BizAssert.notNull(user.getActivityid(), "活动id, 参数错误");
        userService.updateByUserCodeForlottery(user);
        responseDto.successData(Boolean.valueOf(true));
        return responseDto;
    }

    @PostMapping({"/pc/userSelectLottery"})
    public ResponseDto<List<UserUpdatelottery>> userUpdatelottery(UserUpdatelottery user) {
        ResponseDto<List<UserUpdatelottery>> responseDto = new ResponseDto();
        BizAssert.notNull(user.getUsercode(), "usercode为null, 参数错误");
        List<UserUpdatelottery> list = userService.userUpdatelottery(user);
        responseDto.successData(list);
        return responseDto;
    }

    @PostMapping({"/pc/updateUserIntegral"})
    public ResponseDto<Boolean> updateUserIntegral(User user) {
        ResponseDto<Boolean> responseDto = new ResponseDto();
        BizAssert.notNull(user.getId(), "userid不能为空，参数错误");
        userService.updateById(user);
        responseDto.successData(Boolean.valueOf(true));
        return responseDto;
    }

    @PostMapping({"/phone/myRankingByUser"})
    public ResponseDto<PageBean> myRankingByUser(@RequestHeader("token") String token, @RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        ResponseDto<PageBean> responseDto = ResponseDto.successDto();
        PageBean list = userService.myRankingByUser(token, pageNo, pageSize);
        responseDto.setData(list);
        return responseDto;
    }

    @PostMapping({"/phone/integralRecordByUser"})
    public ResponseDto<PageBean<Integral>> integralRecordByUser(@RequestHeader("token") String token, @RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        ResponseDto<PageBean<Integral>> responseDto = ResponseDto.successDto();
        try {
            CommonLoginContext context = tokenService.getContextByken(token);
            PageBean<Integral> list = userService.integralRecordByUser(context.getUserId(), pageNo, pageSize);
            list.setWincount(context.getUser().getIntegral());
            responseDto.setData(list);
            return responseDto;
        } catch (Exception e) {
            log.debug("查询当前用户ID和应援活动和饭团有问题", e.getMessage());
            responseDto.setCode(1);
            return responseDto;
        }
    }

    @PostMapping({"/phone/addintegral"})
    public ResponseDto<Integer> addintegral1(@RequestHeader("token") String token) {
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        try {
            CommonLoginContext context = tokenService.getContextByken(token);
            Integer count = userService.addintegral1(context.getUserId());
            responseDto.setData(count);
            return responseDto;
        } catch (Exception e) {
            log.debug("查询当前用户ID和应援活动和饭团有问题", e.getMessage());
            responseDto.setCode(Integer.valueOf(1));
            return responseDto;
        }
    }

    @CommonExHandler(key = "查询当前用户ID和应援活动和饭团")
    @PostMapping({"/phone/queryUserIdAndActvitity"})
    public ResponseDto<User> queryUserIdAndActvitity(@RequestHeader("token") String token) {
        ResponseDto<User> responseDto = ResponseDto.successDto();
        try {
            CommonLoginContext context = tokenService.getContextByken(token);
            User user = userService.queryUserIdAndActvitity(context.getUserId());
            responseDto.setData(user);
            return responseDto;
        } catch (Exception e) {
            log.debug("查询当前用户ID和应援活动和饭团有问题", e.getMessage());
            responseDto.setCode(Integer.valueOf(1));
            return responseDto;
        }
    }

    @PostMapping({"/updateUserName"})
    public ResponseDto<Boolean> updateUserName(@RequestHeader("token") String token, String name, Integer gender, String avatarurl) {
        ResponseDto<Boolean> responseDto = new ResponseDto();
        this.userService.updateByName(token, name, gender, avatarurl);
        responseDto.successData(Boolean.valueOf(true));
        return responseDto;
    }

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
