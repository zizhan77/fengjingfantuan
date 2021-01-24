package com.mem.vo.business.base.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.mem.vo.business.base.dao.IntegralDao;
import com.mem.vo.business.base.dao.RewardDao;
import com.mem.vo.business.base.dao.UserDao;
import com.mem.vo.business.base.model.po.AgePo;
import com.mem.vo.business.base.model.po.Integral;
import com.mem.vo.business.base.model.po.MtaBean;
import com.mem.vo.business.base.model.po.MtaData;
import com.mem.vo.business.base.model.po.Ranking;
import com.mem.vo.business.base.model.po.User;
import com.mem.vo.business.base.model.po.UserQuery;
import com.mem.vo.business.base.model.vo.UserUpdatelottery;
import com.mem.vo.business.base.service.IntegralService;
import com.mem.vo.business.base.service.UserService;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.constant.IntegralEnum;
import com.mem.vo.common.constant.RedisPrefix;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.PageBean;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.RedisUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <br>
 * <b>功能：</b>UserService<br>
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private final static Logger log = LogManager.getLogger(UserServiceImpl.class);


    @Resource
    private TokenService tokenService;

    @Resource
    private IntegralService integralService;

    @Resource
    private IntegralDao integralDao;

    @Resource
    private UserDao userDao;

    @Resource
    private RewardDao rewardDao;

    @Resource
    private RedisUtils redisUtils;

    @Override
    public int insert(User user) {
        return userDao.insert(user);
    }

    @Override
    public int updateById(User user) {
        return userDao.updateById(user);
    }

    @Override
    public int deleteById(Long id) {
        return userDao.deleteById(id);
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public List<User> findByCondition(UserQuery query) {
        return userDao.findByCondition(query);
    }

    @Override
    public Page<User> findPageByCondition(Page page, UserQuery query) {
        List<User> users = userDao.findByCondition(page, query);
        for (Iterator<User> iterator = users.iterator(); iterator.hasNext(); ) {
            User user = iterator.next();
            if (StringUtils.isBlank(user.getPhoneNumber())) {
                userDao.deleteById(user.getId());
                iterator.remove();
            }
        }
        page.setData(users);
        return page;
    }

    @Override
    public int updateBySourceAndBizCode(String phoneNumber, String sourceCode, String bizCode,String userName,Integer gender, String avatarUrl) {
        return userDao.updateBySourceAndBizCode(phoneNumber,sourceCode,bizCode,userName,gender,avatarUrl);
    }

    @Override
    public MtaBean findGender() {
        List<MtaData> data = userDao.findGender();
        MtaBean mtaBean = new MtaBean();
        mtaBean.setData(data);
        return mtaBean;
    }

    @Override
    public HashMap<String, String> findAge(String list) {
        BizAssert.notNull(list, BizCode.PARAM_NULL.getMessage());
        List<AgePo> agePos = JSONArray.parseArray(list, AgePo.class);
        BizAssert.notNull(agePos, BizCode.PARAM_NULL.getMessage());
        HashMap<String, String> map = new HashMap<>();
        for (AgePo agePo : agePos) {
            String count = userDao.findAge(agePo);
            map.put(agePo.getStartTime() + "到" + agePo.getEndTime() + "岁", count);
        }
        return map;
    }

    @Override
    public void addIntegral(Integer code, String userId) {
        userDao.addIntegral(code, userId);
    }

    @Override
    public int updateIntegral(String bizCode, Integer integral) {
        return userDao.updateIntegral(bizCode, integral);
    }

    @Override
    public void updateByIntegral(Integer num, Long shareId) {
        userDao.updateByIntegral(num, shareId);
    }

    @Override
    public void updateByName(String token, String name, Integer gender, String avatarurl) {
        CommonLoginContext commonLoginContext = this.tokenService.getContextByken(token);
        User user = new User();
        user.setId(commonLoginContext.getUserId());
        user.setName(name);
        user.setGender(gender);
        user.setAvatarurl(avatarurl);
        this.userDao.updateById(user);
    }

    @Override
    public User queryUserIdAndActvitity(Long userId) {
        User user = userDao.findById(userId);
        Integer count = userDao.getAdddessFlag(userId);
        user.setAddress(Integer.valueOf((count == null) ? 0 : count.intValue()));
        Integer whocount = userDao.getUserWhoCount(userId);
        Integer actcount = userDao.getUserActCount(user.getBizCode());
        if (user != null) {
            user.setWhocount(Integer.valueOf((whocount == null) ? 0 : whocount.intValue()));
            user.setActcount(Integer.valueOf((actcount == null) ? 0 : actcount.intValue()));
        }
        return user;
    }

    @Override
    public PageBean<Integral> integralRecordByUser(Long userId, Integer pageNo, Integer pageSize) {
        int Commod = integralService.integralRecordByUserCount(userId);
        PageBean<Integral> pager = new PageBean();
        pager.setPageSize(pageSize);
        pager.setRows(Integer.valueOf(Commod));
        pager.setPageNo(pageNo);
        int first = (pager.getPageNo().intValue() - 1) * pager.getPageSize().intValue();
        pager.setStart(Integer.valueOf(first));
        List<Integral> list1 = integralService.integralRecordByUser(userId, pager);
        pager.setLists(list1);
        return pager;
    }

    @Override
    public PageBean myRankingByUser(String token, Integer pageNo, Integer pageSize) {
        CommonLoginContext contextByken = tokenService.getContextByken(token);
        int Commod = userDao.myRankingByUserCount(contextByken.getUserId());
        PageBean<Ranking> pager = new PageBean();
        pager.setPageSize(pageSize);
        pager.setRows(Integer.valueOf(Commod));
        pager.setPageNo(pageNo);
        int first = (pager.getPageNo().intValue() - 1) * pager.getPageSize().intValue();
        pager.setStart(Integer.valueOf(first));
        List<Ranking> list = userDao.myRankingByUser(pager, contextByken.getUserId());
        int count = 0;
        for (Ranking r : list) {
            String s = redisUtils.get(RedisPrefix.RANKING_USER + r.getId().toString());
            if (s != null && s.length() > 0) {
                String[] split = s.split(",");
                for (String string : split) {
                    if (string.equals(r.getId().toString())) {
                        count++;
                        r.setIsusertop(Integer.valueOf(1));
                    }
                }
            }
        }
        pager.setWincount(Integer.valueOf(count));
        pager.setLists(list);
        return pager;
    }

    @Override
    public Integer addintegral1(Long userId) {
        User user = userDao.findById(userId);
        if (user != null && user.getIntegralflag().intValue() == 0) {
            Integer count = rewardDao.getUserIntegralFlagCount();
            if (count.intValue() > 0) {
                Integer insertuser = userDao.insertIntegralPageage(count, 1, user.getId());
                if (insertuser != null && insertuser.intValue() > 0) {
                    Integral in = Integral.builder()
                            .activityName("首次注册礼包")
                            .integralQty(Integer.valueOf(user.getIntegral().intValue() + count.intValue()))
                            .type(IntegralEnum.TYPE_GET.getCode())
                            .userId(Integer.valueOf(user.getId().intValue()))
                            .build();
                    int insert = integralDao.insert(in);
                    if (insert == 0) {
                        throw new BizException("饭团操作记录写入失败");
                    }
                    return insertuser;
                }
            } else {
                throw new BizException("查找饭团首次礼包失败");
            }
        } else {
            return 0;
        }
        return 0;
    }

    @Override
    public List<UserUpdatelottery> userUpdatelottery(UserUpdatelottery user) {
        List<UserUpdatelottery> list = userDao.userUpdatelottery(user);
        for (UserUpdatelottery u : list) {
            u.setUsercode(user.getUsercode());
            Integer count = userDao.getlotteryqtyByuserCode(u);
            if (count != null) {
                u.setLotteryqty(count);
                continue;
            }
            u.setLotteryqty(Integer.valueOf(0));
        }
        return list;
    }

    @Override
    public void updateByUserCodeForlottery(UserUpdatelottery user) {
        Integer count = userDao.selectByCodeAndActid(user);
        if (count != null && count.intValue() > 0) {
            this.userDao.updateByUserCodeForlottery(user);
        } else {
            this.userDao.insertByCodeAndActid(user);
        }
    }
}
