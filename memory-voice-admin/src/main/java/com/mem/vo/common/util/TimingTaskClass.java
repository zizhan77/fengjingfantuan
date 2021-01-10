package com.mem.vo.common.util;
import com.mem.vo.business.base.dao.ActivityDao;
import com.mem.vo.business.base.model.po.User;
import com.mem.vo.common.constant.RedisPrefix;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class TimingTaskClass {
    @Resource
    private RedisUtils redisUtils;

    @Resource
    private ActivityDao activityDao;

    @Scheduled(cron = "0 0 0/1 * * ? ")
    public void bannerLineFlag() {
        List<Integer> activity = activityDao.getActivityEnd();
        if (activity != null && activity.size() > 0) {
            for (Integer i : activity) {
                List<User> list = activityDao.getActivityUser(i);
                String str = "";
                if (list != null && list.size() > 0) {
                    for (User user : list) {
                        str = str + "," + user.getId();
                    }
                }
                if (str.length() > 0) {
                    String substring = str.substring(1, str.length() - 1);
                    redisUtils.setex(RedisPrefix.RANKING_USER + i.toString(), substring, 604800);
                }
            }
        }
    }
}
