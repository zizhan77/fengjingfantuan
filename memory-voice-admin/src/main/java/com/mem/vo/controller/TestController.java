package com.mem.vo.controller;

import com.mem.vo.MemVoAdminApplication;
import com.mem.vo.controller.user.TestModel;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;

/**
 * @author litongwei
 * @description:
 * @date 2019/6/10 17:47
 */
@Controller
public class TestController {

    @RequestMapping("/test")
    public String test(){
        return "index";
    }

    public static void main(String[] args) {
        TestModel t = new TestModel();
        t.setADecimal(new BigDecimal("0.1"));
        //BigDecimal b = new BigDecimal("0.1");
        int aa = t.getADecimal().multiply(new BigDecimal("100")).intValue();
        System.out.println(aa);
    }
    public class Gen <A extends Serializable & Comparator> {
        //code
    }

}
