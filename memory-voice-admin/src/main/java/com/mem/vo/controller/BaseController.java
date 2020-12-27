package com.mem.vo.controller;

import java.util.Optional;

import javax.annotation.Resource;

import com.mem.vo.business.base.dao.UserDao;
import com.mem.vo.business.base.model.po.User;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.util.JsonUtil;
import com.mem.vo.common.util.RedisUtils;
import com.mem.vo.config.annotations.CommonExHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by litongwei on 2019/5/19.
 */

@RestController
public class BaseController {

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private UserDao userDao;

    @RequestMapping("/")
    @CommonExHandler(key = "欢迎页面异常")
    public ResponseDto index(@RequestHeader("token") String token) {
        System.out.println("!111111111111111");
        System.out.println("!222222222222222222");

        return new ResponseDto().successData("mem vo 欢迎你!");
    }

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

    @RequestMapping("/hello11")
    @CommonExHandler(key = "hell11业务")
    public ResponseDto<String> hello11(@RequestHeader("token") String token) {
        int a = 2/0;
        return new ResponseDto<>().successData("Hello World!");
    }

    @RequestMapping("/findUser")
    public String findUser() {
        User user = userDao.findById(1L);

        return user==null?"": JsonUtil.toJson(user);
    }

    @GetMapping("/setkey/{key}/{value}")
    public Optional<Boolean> setkey(@PathVariable String key,@PathVariable String value) {
        return Optional.ofNullable(redisUtils.set(key,value));
    }


    @GetMapping("/getkey/{key}")
    public Optional<String> testRedis(@PathVariable String key) {
        return Optional.ofNullable(redisUtils.get(key));
    }


    @GetMapping("/delkey/{key}")
    public Optional<Boolean> delkey(@PathVariable String key) {
        return Optional.ofNullable(redisUtils.del(key));
    }

}
