package com.mem.vo;

import com.alibaba.fastjson.JSON;
import com.mem.vo.business.base.model.po.User;
import com.mem.vo.business.base.model.po.UserQuery;
import com.mem.vo.business.base.service.ExchangeCodeMainService;
import com.mem.vo.business.base.service.UserService;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.util.JsonUtil;
import com.mem.vo.common.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author litongwei
 * @description:
 * @date 2019/5/22 14:58
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTests.class)
public class BaseTest {

    @Resource
    private UserService userService;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private ExchangeCodeMainService exchangeCodeMainService;

    @Test
    public void test(){
        UserQuery userQuery = new UserQuery();
        List<User> users =  userService.findByCondition(userQuery);
        System.out.println(JSON.toJSON(users));
    }

    @Test
    public void testPage(){
        Page<User> page = new Page<>();
        page.setPage(3);
        UserQuery userQuery = new UserQuery();
        Page<User> pageByCondition = userService.findPageByCondition(page,userQuery);
        System.out.println(JsonUtil.toJson(pageByCondition));

    }

    @Test
    public void testRedis() {
        redisUtils.set("AAA", "BBBCCC");
        System.out.println(redisUtils.get("AAA"));
    }

    @Test
    public void testTransaction(){
        exchangeCodeMainService.testTrans();

    }

}
