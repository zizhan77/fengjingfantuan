package com.mem.vo.controller.authority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.mem.vo.business.base.model.po.*;
import com.mem.vo.business.base.service.AuthorityMenuService;
import com.mem.vo.business.base.service.AuthorityRoleMenuRelService;
import com.mem.vo.business.base.service.AuthorityRoleService;
import com.mem.vo.business.biz.model.vo.authority.AuthorityRoleVo;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.BeanCopyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author litongwei
 * @description:
 * @date 2019/6/3 18:00
 */
@RestController
@RequestMapping("/authority/role")
@Slf4j
public class AuthorityRoleController {

    @Resource
    private AuthorityRoleService authorityRoleService;

    @Resource
    private AuthorityRoleMenuRelService authorityRoleMenuRelService;

    @Resource
    private AuthorityMenuService authorityMenuService;


    @PostMapping("/addRole")
    public ResponseDto<Long> addRole(@RequestHeader("token") String token, String roleName) {
        ResponseDto<Long> responseDto = ResponseDto.successDto();

        try {
            AuthorityRole authorityRole = new AuthorityRole();
            authorityRole.setName(roleName);
            authorityRoleService.insert(authorityRole);

            return responseDto.successData(authorityRole.getId());

        } catch (BizException e) {

            log.error("新增角色异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("新增角色系统异常", e);
            return responseDto.failSys();

        }
    }


    @PostMapping("/queryList")
    public ResponseDto<List<AuthorityRoleVo>> queryList(@RequestHeader("token") String token) {
        ResponseDto<List<AuthorityRoleVo>> responseDto = ResponseDto.successDto();

        try {
            AuthorityRoleQuery query = new AuthorityRoleQuery();
            List<AuthorityRole> list = authorityRoleService.findByCondition(query);
            List<AuthorityRoleVo> voList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(list)) {
                voList = list.stream().map(item -> {
                    AuthorityRoleVo vo = BeanCopyUtil.copyProperties(item, AuthorityRoleVo.class);
                    AuthorityRoleMenuRelQuery relQuery = new AuthorityRoleMenuRelQuery();
                    relQuery.setRoleId(item.getId());
                    List<AuthorityMenu> menuList = new ArrayList<AuthorityMenu>();
                    List<AuthorityRoleMenuRel> menuRels = authorityRoleMenuRelService.findByCondition(relQuery);
                    if (CollectionUtils.isNotEmpty(menuRels)) {
                        for(AuthorityRoleMenuRel menuRel: menuRels){
                            AuthorityMenu authorityMenu = authorityMenuService.findById(Long.valueOf(menuRel.getMenuId()));
                            menuList.add(authorityMenu);
                        }
                        vo.setAuthorityMenuList(menuList);
                    }

                    return vo;
                }).collect(Collectors.toList());
            }

            return responseDto.successData(voList);

        } catch (BizException e) {

            log.error("查询角色列表异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("查询角色列表系统异常", e);
            return responseDto.failSys();

        }
    }

    @PostMapping("/deleteRole")
    public ResponseDto<Boolean> deleteRole(@RequestHeader("token") String token, Long roleId) {
        ResponseDto<Boolean> responseDto = ResponseDto.successDto();

        try {

            authorityRoleService.deleteById(roleId);

            return responseDto.successData(true);

        } catch (BizException e) {

            log.error("删除角色异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("删除角色系统异常", e);
            return responseDto.failSys();

        }
    }



}
