package com.mem.vo.sms;

import com.mem.vo.business.biz.service.sms.SmsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-06-30 12:09
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsSendTest {

    @Resource
    private SmsService smsService;

    @Test
    public void test() {
        System.out.println(smsService.sendSms("18911717331"));
    }
}
