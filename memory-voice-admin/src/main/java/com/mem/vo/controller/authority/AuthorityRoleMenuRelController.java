package com.mem.vo.controller.authority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.mem.vo.business.base.model.po.AuthorityRoleMenuRel;
import com.mem.vo.business.base.service.AuthorityRoleMenuRelService;
import com.mem.vo.business.base.service.AuthorityRoleService;
import com.mem.vo.business.base.service.UserService;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.model.vo.performance.RoleMenusRelVo;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.JsonUtil;
import com.mem.vo.common.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author litongwei
 * @description:
 * @date 2019/5/31 15:00
 */

@RestController
@RequestMapping("/authority")
@Slf4j
public class AuthorityRoleMenuRelController {

    @Resource
    private AuthorityRoleMenuRelService authorityRoleMenuRelService;

    @Resource
    private UserService userService;


    @Resource
    private RedisUtils redisUtils;

    @Resource
    private AuthorityRoleService authorityRoleService;

    @Resource
    private TokenService tokenService;


    @PostMapping("/addRoleMenusRel")
    public ResponseDto<Boolean> addRoleMenusRel(@RequestHeader("token") String token,
        @RequestBody List<RoleMenusRelVo> roleMenusRelVoList) {
        ResponseDto<Boolean> responseDto = ResponseDto.successDto();

        try {
            BizAssert.notEmpty(roleMenusRelVoList,"保存的列表为空");
            List<Integer> roleIds = roleMenusRelVoList.stream().map(i -> i.getRoleId()).collect(Collectors.toList());
            CommonLoginContext context = tokenService.getContextByken(token);
            BizAssert.notNull(context,"根据 token 获取登录信息为空");
            authorityRoleService.updateByRoleIds(roleIds,context.getUserId()==null?"":context.getUserId().toString());
            roleMenusRelVoList.forEach(roleMenusRelVo->{
                List<Integer> menus = roleMenusRelVo.getMenuIds();
                menus.forEach(item -> {
                    AuthorityRoleMenuRel authorityRoleMenuRel = new AuthorityRoleMenuRel();
                    authorityRoleMenuRel.setRoleId(roleMenusRelVo.getRoleId());
                    authorityRoleMenuRel.setMenuId(item);
                    authorityRoleMenuRelService.insert(authorityRoleMenuRel);
                });

            });


            return responseDto.successData(true);

        } catch (BizException e) {

            log.error("保存角色和菜单业务异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("保存角色和菜单异常", e);
            return responseDto.failSys();

        }
    }

    public static void main(String[] args) {
        List<RoleMenusRelVo> roleMenusRelVoList = new ArrayList<>();
        RoleMenusRelVo vo1 = new RoleMenusRelVo();
        vo1.setRoleId(1);
        List<Integer> menus = new ArrayList<>();
        menus.add(4);
        menus.add(5);
        vo1.setMenuIds(menus);
        roleMenusRelVoList.add(vo1);


        RoleMenusRelVo vo2 = new RoleMenusRelVo();
        vo2.setRoleId(2);
        List<Integer> menus2 = new ArrayList<>();
        menus.add(2);
        menus.add(3);
        vo2.setMenuIds(menus);
        roleMenusRelVoList.add(vo2);
        System.out.println(JsonUtil.toJson(roleMenusRelVoList));

    }
    //获取角色权限
    /*@PostMapping("/queryRolesAndMenus")
    public ResponseDto<> queryRolesAndMenus(@RequestHeader("token") String token) {
        ResponseDto<UserExtend> responseDto = ResponseDto.successDto();

        UserExtendQuery query = new UserExtendQuery();
        String json= redisUtils.get("token_"+ token);
        CommonLoginContext commonLoginContext = JsonUtil.fromJson(json,CommonLoginContext.class);
        query.setMainId(commonLoginContext.getUserId().longValue());
        List<UserExtend> UserExtendList = userExtendService.findByCondition(query);

        try {
            if(UserExtendList.size() > 0){
                return responseDto.successData(UserExtendList.get(0));
            }else{
                throw new BizException(BizCode.BIZ_1012.getMessage());
            }
        } catch (BizException e) {

            log.error("获取角色和菜单业务异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("获取角色和菜单异常", e);
            return responseDto.failSys();

        }
    }*/
}
