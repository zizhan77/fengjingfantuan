package com.mem.vo.controller.authority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.mem.vo.business.base.model.po.AuthorityMenu;
import com.mem.vo.business.base.model.po.AuthorityMenuQuery;
import com.mem.vo.business.base.service.AuthorityMenuService;
import com.mem.vo.business.biz.model.vo.authority.AuthorityMenuVo;
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
 * @date 2019/6/3 18:27
 */

@RestController
@RequestMapping("/authority/menu")
@Slf4j
public class AuthorityMenuController {

    @Resource
    private AuthorityMenuService authorityMenuService;

    @PostMapping("/queryList")
    public ResponseDto<List<AuthorityMenuVo>> queryList(@RequestHeader("token") String token) {
        ResponseDto<List<AuthorityMenuVo>> responseDto = ResponseDto.successDto();

        try {
            AuthorityMenuQuery query = new AuthorityMenuQuery();
            List<AuthorityMenu> list = authorityMenuService.findByCondition(query);
            List<AuthorityMenuVo> voList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(list)) {
                voList = list.stream().map(item -> {
                    AuthorityMenuVo vo = BeanCopyUtil.copyProperties(item, AuthorityMenuVo.class);
                    return vo;
                }).collect(Collectors.toList());
            }

            return responseDto.successData(voList);

        } catch (BizException e) {

            log.error("查询菜单列表异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("查询菜单列表系统异常", e);
            return responseDto.failSys();

        }
    }
}
