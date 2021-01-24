package com.mem.vo.business.base.service.impl;

import com.mem.vo.business.base.dao.IntegralDao;
import com.mem.vo.business.base.dao.RewardDao;
import com.mem.vo.business.base.dao.SignInUserDao;
import com.mem.vo.business.base.dao.UserDao;
import com.mem.vo.business.base.model.po.Integral;
import com.mem.vo.business.base.model.po.IntegralRoleClass;
import com.mem.vo.business.base.model.po.UserSignClass;
import com.mem.vo.business.base.service.SignInUserService;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.IntegralEnum;
import com.mem.vo.common.exception.BizException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;


@Service
public class SignInUserServiceImpl implements SignInUserService {

    @Resource
    private TokenService tokenService;

    @Resource
    private SignInUserDao signInUserDao;

    @Resource
    private UserDao userDao;

    @Resource
    private RewardDao rewardDao;

    @Resource
    private IntegralDao integralDao;

    @Override
    public UserSignClass signIntegral(String token) {
        UserSignClass userSignClass = new UserSignClass();
        CommonLoginContext contextByken = tokenService.getContextByken(token);
        Integer allCount = signInUserDao.getUserAllIntegral(contextByken.getUserId());
        if (allCount != null) {
            userSignClass.setAllIntegral(allCount);
        } else {
            userSignClass.setAllIntegral(Integer.valueOf(0));
        }
        Integer today = signInUserDao.getUserTodayIntegral(contextByken.getUserId());
        if (today != null) {
            userSignClass.setTodayIntegral(today);
        } else {
            userSignClass.setTodayIntegral(Integer.valueOf(0));
        }
        return userSignClass;
    }

    @Override
    public UserSignClass signShow(String token) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(5, -1);
        String time = format.format(c.getTime());
        String format1 = format.format(new Date());
        CommonLoginContext contextByken = tokenService.getContextByken(token);
        UserSignClass userSignClass = signInUserDao.getsignShow(contextByken.getUserId());
        if (userSignClass != null) {
            if (userSignClass.getSignendtime() != null) {
                if (userSignClass.getSignendtime().equals(format1)) {
                    userSignClass.setIsSignToday(1);
                } else if (userSignClass.getSignendtime().equals(time)) {
                    userSignClass.setIsSignToday(0);
                } else {
                    userSignClass.setLength(0);
                }
            } else {
                userSignClass.setLength(0);
            }
        } else {
            userSignClass = new UserSignClass();
            userSignClass.setSignstarttime(format1);
            userSignClass.setTodayIntegral(0);
            userSignClass.setLength(0);
        }
        if (userSignClass.getLength() != null) {
            if (userSignClass.getLength().toString().equals("0")) {
                userSignClass.setLight(0);
            } else if (userSignClass.getLength().intValue() % 7 == 0) {
                userSignClass.setLight(8);
            } else {
                userSignClass.setLight(userSignClass.getLength().intValue() % 7);
            }
        } else {
            userSignClass.setLight(0);
            userSignClass.setLength(0);
        }
        return userSignClass;
    }

    @Override
    public Integer signAddIntegral(String token) {
        CommonLoginContext contextByken = tokenService.getContextByken(token);
        UserSignClass userSignClass = signInUserDao.getsignShow(contextByken.getUserId());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(5, -1);
        String time = format.format(c.getTime());
        String format1 = format.format(new Date());
        if (userSignClass != null) {
            if (userSignClass.getSignendtime() != null) {
                if (userSignClass.getSignendtime().equals(format1)) {
                    return Integer.valueOf(1);
                }
                if (userSignClass.getSignendtime().equals(time)) {
                    Integer length = userSignClass.getLength();
                    userSignClass.setLength(Integer.valueOf(length.intValue() + 1));
                    userSignClass.setSignendtime(format1);
                    Integer integer1 = updateUserSign(userSignClass);
                    return integer1;
                }
                userSignClass.setLength(Integer.valueOf(1));
                userSignClass.setSignendtime(format1);
                userSignClass.setSignstarttime(format1);
                Integer integer = updateUserSign(userSignClass);
                return integer;
            }
        } else {
            UserSignClass u = new UserSignClass();
            u.setLength(Integer.valueOf(1));
            u.setCreatetime(format1);
            u.setSignendtime(format1);
            u.setSignstarttime(format1);
            u.setUpdatetime(format1);
            u.setUserid(contextByken.getUserId());
            Integer integer = addUserSign(u);
            return integer;
        }
        return Integer.valueOf(0);
    }

    @Override
    public Integer signAddIntegralTest(Long userid) {
        UserSignClass userSignClass = this.signInUserDao.getsignShow(userid);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(5, -1);
        String time = format.format(c.getTime());
        String format1 = format.format(new Date());
        if (userSignClass != null) {
            if (userSignClass.getSignendtime() != null) {
                if (userSignClass.getSignendtime().equals(format1)) {
                    return Integer.valueOf(1);
                }
                if (userSignClass.getSignendtime().equals(time)) {
                    Integer length = userSignClass.getLength();
                    userSignClass.setLength(Integer.valueOf(length.intValue() + 1));
                    userSignClass.setSignendtime(format1);
                    Integer integer1 = updateUserSign(userSignClass);
                    return integer1;
                }
                userSignClass.setLength(Integer.valueOf(1));
                userSignClass.setSignendtime(format1);
                userSignClass.setSignstarttime(format1);
                Integer integer = updateUserSign(userSignClass);
                return integer;
            }
        } else {
            UserSignClass u = new UserSignClass();
            u.setLength(Integer.valueOf(1));
            u.setCreatetime(format1);
            u.setSignendtime(format1);
            u.setSignstarttime(format1);
            u.setUpdatetime(format1);
            u.setUserid(userid);
            Integer integer = addUserSign(u);
            return integer;
        }
        return 0;
    }

    public Integer addUserSign(UserSignClass userSignClass) {
        int flag = signInUserDao.addUserSign(userSignClass);
        List<IntegralRoleClass> list = rewardDao.integralRoleList(Integer.valueOf(2));
        IntegralRoleClass integralRoleClass = new IntegralRoleClass();
        if (list != null && list.size() > 0) {
            integralRoleClass = list.get(0);
        } else {
            integralRoleClass.setAddcount(Integer.valueOf(16));
            integralRoleClass.setOtherjson("3,100-7,300");
        }
        int count = integralRoleClass.getAddcount().intValue();
        Integer insertuser = userDao.insertIntegralPageage(Integer.valueOf(count), 1, userSignClass.getUserid());
        if (insertuser != null && insertuser.intValue() > 0) {
            Integral in = Integral.builder().activityName("签到记录").integralQty(Integer.valueOf(count)).type(IntegralEnum.TYPE_GET.getCode()).userId(Integer.valueOf(userSignClass.getUserid().intValue())).build();
            int insert = integralDao.insert(in);
            if (insert == 0) {
                throw new BizException("饭团操作记录写入失败");
            }
        }
        return Integer.valueOf(flag);
    }

    public Integer updateUserSign(UserSignClass userSignClass) {
        int flag = signInUserDao.updateUserSign(userSignClass);
        List<IntegralRoleClass> list = rewardDao.integralRoleList(Integer.valueOf(2));
        IntegralRoleClass integralRoleClass = new IntegralRoleClass();
        if (list != null && list.size() > 0) {
            integralRoleClass = list.get(0);
        } else {
            integralRoleClass.setAddcount(Integer.valueOf(16));
            integralRoleClass.setOtherjson("3,100-7,300");
        }
        int count = integralRoleClass.getAddcount().intValue();
        Integer len = Integer.valueOf(userSignClass.getLength().intValue() % 7);
        String otherjson = integralRoleClass.getOtherjson();
        String[] split = otherjson.split("-");
        for (String str : split) {
            String[] split1 = str.split(",");
            if (split1.length > 0) {
                if (len.toString().equals("0") &&
                        split1[0].equals("7")) {
                    int i = Integer.parseInt(split1[1]);
                    count += i;
                }
                if (split1[0].equals(len.toString())) {
                    int i = Integer.parseInt(split1[1]);
                    count += i;
                }
            }
        }
        Integer insertuser = userDao.insertIntegralPageage(Integer.valueOf(count), 1, userSignClass.getUserid());
        if (insertuser != null && insertuser.intValue() > 0) {
            Integral in = Integral.builder()
                    .activityName("签到记录")
                    .integralQty(Integer.valueOf(count))
                    .type(IntegralEnum.TYPE_GET.getCode())
                    .userId(Integer.valueOf(userSignClass.getUserid().intValue()))
                    .build();
            int insert = integralDao.insert(in);
            if (insert == 0) {
                throw new BizException("饭团操作记录写入失败");
            }
        }
        return Integer.valueOf(flag);
    }
}
