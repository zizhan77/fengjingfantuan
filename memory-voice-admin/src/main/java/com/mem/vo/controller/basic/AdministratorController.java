package com.mem.vo.controller.basic;

import com.alibaba.fastjson.JSONObject;
import com.mem.vo.business.base.model.po.AuthorityRole;
import com.mem.vo.business.base.model.po.User;
import com.mem.vo.business.base.model.po.UserQuery;
import com.mem.vo.business.base.service.AuthorityRoleService;
import com.mem.vo.business.base.service.UserService;
import com.mem.vo.business.biz.model.vo.performance.AdministratorVo;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.RoleType;
import com.mem.vo.common.constant.SourceType;
import com.mem.vo.common.constant.UserStatus;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.BeanCopyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author litongwei
 * @description:
 * @date 2019/5/31 14:38
 */

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdministratorController {

    @Resource
    private UserService userService;

    @Resource
    private AuthorityRoleService authorityRoleService;

    @Resource
    private TokenService tokenService;


    @PostMapping("/add/administrator")
    public ResponseDto<Boolean> addAdministrator(@RequestHeader("token") String token,
        AdministratorVo administratorVo) {
        ResponseDto<Boolean> responseDto = ResponseDto.successDto();

        try {
            User user = new User();
            user.setPhoneNumber(administratorVo.getAccountNumber());
            user.setPassword(administratorVo.getPassword());
            user.setBizCode(administratorVo.getAccountNumber());
            user.setSource(SourceType.PC_ADMIN.getCode());
            user.setSourceName(SourceType.PC_ADMIN.getDescription());
            user.setStatus(UserStatus.ENABLE.getCode());
            user.setRole(administratorVo.getRole());
            user.setIsDelete(0);
            UserQuery userQuery = BeanCopyUtil.copyProperties(user,UserQuery.class);
            List<User> userList = userService.findByCondition(userQuery);
            if(CollectionUtils.isNotEmpty(userList)){
                throw new BizException("用户已存在，无法新增");
            }
            userService.insert(user);

            return responseDto.successData(true);

        } catch (BizException e) {

            log.error("保存管理员业务异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("保存管理员异常", e);
            return responseDto.failSys();

        }
    }


    @PostMapping("/getList")
    public ResponseDto<JSONObject> getList(@RequestHeader("token") String token, Page page) {
        ResponseDto<JSONObject> responseDto = ResponseDto.successDto();

        try {
            JSONObject jsonObject = new JSONObject();
            UserQuery query = new UserQuery();
            query.setSource(SourceType.PC_ADMIN.getCode());
            List<Integer> roleList = new ArrayList<>();
            roleList.add(RoleType.SUPER_ADMIN.getCode());
            roleList.add(RoleType.ADMIN.getCode());
            query.setRoleList(roleList);

            Page<User> resPage = userService.findPageByCondition(page, query);

            if (CollectionUtils.isNotEmpty(resPage.getData())) {

                List<User> allUser = resPage.getData();
                List<AdministratorVo> reslist = new ArrayList<>();
                allUser.forEach(item -> {
                    AdministratorVo vo = BeanCopyUtil.copyProperties(item, AdministratorVo.class);
                    vo.setAccountNumber(item.getPhoneNumber());
                    //vo.setPassword(item.getPhoneNumber());

                    if (vo.getRole() != null) {
                        AuthorityRole role = authorityRoleService.findById(Long.valueOf(vo.getRole()));
                        if (role != null) {
                            vo.setRoleName(role.getName());
                        }
                    }
                    //状态转换
                    reslist.add(vo);

                });
                jsonObject.put("total",resPage.getTotal());
                jsonObject.put("list",reslist);


                return responseDto.successData(jsonObject);
            }
            return responseDto;

        } catch (BizException e) {

            log.error("获取管理员列表业务异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("获取管理员列表异常", e);
            return responseDto.failSys();

        }
    }


    @PostMapping("/updateAble")
    public ResponseDto<Boolean> updateAble(@RequestHeader("token") String token,
                                           Long id ,Integer status) {
        ResponseDto<Boolean> responseDto = ResponseDto.successDto();

        try {
            User user = new User();
            user.setId(id);
            user.setStatus(status);
            userService.updateById(user);
            tokenService.delteTokenByUserId(user.getId());
            return responseDto.successData(true);

        } catch (BizException e) {

            log.error("修改启用停用状态业务异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("修改启用停用状态异常", e);
            return responseDto.failSys();

        }
    }



    @PostMapping("/updatePassword")
    public ResponseDto<Boolean> updatePassword(@RequestHeader("token") String token,
                                           Long userId ,String phone ,String  password ,Integer role ) {
        ResponseDto<Boolean> responseDto = ResponseDto.successDto();

        try {
            User user = new User();
            user.setId(userId);
            user.setPhoneNumber(phone);
            user.setPassword(password);
            userService.updateById(user);
            tokenService.delteTokenByUserId(userId);
            return responseDto.successData(true);

        } catch (BizException e) {

            log.error("修改启用停用状态业务异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("修改启用停用状态异常", e);
            return responseDto.failSys();

        }
    }


}
