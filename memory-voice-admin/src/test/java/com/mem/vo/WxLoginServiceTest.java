package com.mem.vo;

import com.mem.vo.business.base.model.po.User;
import com.mem.vo.business.base.service.UserService;
import com.mem.vo.business.biz.model.dto.WxJsTokenResponse;
import com.mem.vo.business.biz.service.login.WxLoginService;
import com.mem.vo.common.util.JsonUtil;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author litongwei
 * @description
 * @date 2019/7/6 23:09
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTests.class)
@Log4j2
public class WxLoginServiceTest {


    @Resource
    private WxLoginService wxLoginService;

    @Resource
    private UserService userService;

    @Test
    public void testSign(){
        for (int i = 0; i < 100; i++) {

            try {
                WxJsTokenResponse response = wxLoginService.getWxSign("");
                System.out.println("返回结果：" +JsonUtil.toJson(response));
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    @Test
    public void testUserName(){
        User user = new User();
        user.setName("good\uD83C\uDF19");
        userService.insert(user);
    }
}
