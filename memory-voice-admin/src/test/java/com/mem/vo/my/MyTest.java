package com.mem.vo.my;

import com.mem.vo.ApplicationTests;
import com.mem.vo.business.base.model.po.Order;
import com.mem.vo.business.base.model.po.OrderQuery;
import com.mem.vo.business.base.model.po.UserLikeQuery;
import com.mem.vo.business.base.model.vo.UserLikeVO;
import com.mem.vo.business.base.service.OrderService;
import com.mem.vo.business.base.service.UserLikeService;
import com.mem.vo.common.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lvxiao
 * @date 2019/7/5
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTests.class)
public class MyTest {

    @Resource
    private UserLikeService userLikeService;
    @Resource
    private OrderService orderService;

    @Test
    public void test1() {
        UserLikeQuery query = new UserLikeQuery();
        query.setUserId(1);
        List<UserLikeVO> vos = userLikeService.selectAll(query);
        System.out.println(JsonUtil.toJson(vos));
    }
    @Test
    public void test2() {
        UserLikeQuery query = new UserLikeQuery();
        query.setUserId(1);
        query.setPerformanceId(100);
        userLikeService.deleteById(query);
    }
    @Test
    public void findMyOrderListByUserId() {
        OrderQuery query = new OrderQuery();
        query.setUserId(27L);
        query.setStatus(0);
        System.out.println(JsonUtil.toJson(orderService.findMyOrderListByUserId(query)));
    }
}
