package com.mem.vo.activity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mem.vo.ApplicationTests;
import com.mem.vo.business.base.dao.ActivityQaDao;
import com.mem.vo.business.base.dao.ActivityUserDao;
import com.mem.vo.business.base.dao.PerformanceDao;
import com.mem.vo.business.base.dao.UserLikeDao;
import com.mem.vo.business.base.model.po.*;
import com.mem.vo.business.base.model.vo.UserLikeVO;
import com.mem.vo.business.base.service.ActivityService;
import com.mem.vo.business.base.service.ActivityUserService;
import com.mem.vo.business.base.service.UserLikeService;
import com.mem.vo.common.util.JsonUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-06-16 17:24
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTests.class)
public class AtcivityTest {

    @Resource
    private ActivityService activityService;
    @Resource
    private ActivityQaDao activityQaDao;

    @Test
    public void testInert() {
        Activity activity = new Activity();
        activity.setActivityTitle("setActivityTitle");
        activity.setActivityIntro("setActivityIntro");
        activity.setType(0);
        activity.setStartDate((new Date()).toString());
        activity.setEndDate((new Date()).toString());
        activity.setStatus(0);
        activity.setSort(1);
        activity.setActivityUrl("setActivityUrl1");
        activity.setCreateTime(new Date());
        activity.setCreateUser("setCreateUser");
        activity.setUpdateTime(new Date());
        activity.setUpdateUser("setUpdateUser");
        activity.setIsDelete(0);
        activity.setTs(new Date());
        Assert.assertEquals(1,activityService.insert(activity));
    }

    @Test
    public void testSelect() {
        Activity activity = activityService.findById(1L);
        System.out.println(JsonUtil.toJson(activity));
    }
    @Resource
    private ActivityUserDao activityUserDao;


    @Test
    public void testQa() {
        List<ActivityQa> activityQaList = activityQaDao.findByCondition(new ActivityQaQuery());
        System.out.println(JsonUtil.toJson(activityQaList));
    }

    @Resource
    private UserLikeService userLikeService;

    @Resource
    private PerformanceDao performanceDao;
    @Test
    public void testUser() {
        UserLikeQuery query = new UserLikeQuery();
        query.setUserId(1);
        System.out.println(JsonUtil.toJson(userLikeService.findByCondition(query)));
    }

    @Autowired
    private ActivityUserService activityUserService;

    @Test
    public void test1() {
        ActivityUser activityUser = new ActivityUser();
        activityUser.setActivityId(2);
        activityUser.setUserId("op41H40mWrGHwxcYRfC3k2o8iTFM");
        activityUser.setPassQty(12);
        activityUserService.updateUserPassQty(activityUser);
    }
}
