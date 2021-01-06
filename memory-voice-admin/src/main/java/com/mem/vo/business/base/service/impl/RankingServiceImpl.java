package com.mem.vo.business.base.service.impl;

import com.mem.vo.business.base.dao.ActivityDao;
import com.mem.vo.business.base.dao.RankingDao;
import com.mem.vo.business.base.dao.UserDao;
import com.mem.vo.business.base.model.po.Integral;
import com.mem.vo.business.base.model.po.Ranking;
import com.mem.vo.business.base.model.po.RankingMoney;
import com.mem.vo.business.base.model.po.User;
import com.mem.vo.business.base.model.vo.RankingVO;
import com.mem.vo.business.base.service.IntegralService;
import com.mem.vo.business.base.service.RankingService;
import com.mem.vo.business.base.service.UserService;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.IntegralEnum;
import com.mem.vo.common.constant.RedisPrefix;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.PageBean;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.RedisUtils;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("rankingService")
@Transactional
public class RankingServiceImpl implements RankingService{

    @Resource
    private RankingDao rankingDao;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private IntegralService integralService;

    @Resource
    private ActivityDao activityDao;

    @Resource
    private UserDao userDao;

    @Resource
    private UserService userService;

    @Resource
    private TokenService tokenService;

    @Override
    public Ranking edit(Ranking ranking) {
        if (ranking.getId() != null && !"".equals(ranking.getId())) {
            int update = rankingDao.update(ranking);
            if (update == 0) {
                throw new BizException("修改失败");
            }
            return ranking;
        }
        Integer add = Integer.valueOf(rankingDao.insert(ranking));
        if (add.intValue() == 0) {
            throw new BizException("添加失败");
        }
        ranking.setId(Long.valueOf(add.longValue()));
        return ranking;
    }

    @Override
    public List<Ranking> query(Ranking ranking) {
        return rankingDao.query(ranking);
    }

    @Override
    public Page<RankingVO> queryPage(Ranking ranking, Page page) {
        List<RankingVO> list = rankingDao.queryPage(ranking, page);
        page.setData(list);
        return page;
    }

    @Override
    public Ranking queryById(String paramString) {
        return null;
    }

    @Override
    public Integer add(String token, String id, String count) {
        CommonLoginContext contextByken = this.tokenService.getContextByken(token);
        User user = this.userService.findById(Long.valueOf(contextByken.getUserId().longValue()));
        System.out.println("投之前的饭团"+ user.getIntegral());
        Integer integral = user.getIntegral();
        if (integral.intValue() < Integer.parseInt(count)) {
            throw new BizException("你没有那么多饭团");
        }
        int i1 = addIntegral(id, count);
        if (i1 == 0) {
            throw new BizException("排行榜增加饭团操作失败");
        }
        Integral in = Integral.builder().activityId(Integer.valueOf(Integer.parseInt(id))).integralQty(Integer.valueOf(0 - Integer.parseInt(count))).type(IntegralEnum.TYPE_USE.getCode()).userId(Integer.valueOf(contextByken.getUserId().intValue())).activityName("投饭团").build();
        int insert = this.integralService.insert(in);
        if (insert == 0) {
            throw new BizException("饭团操作记录写入失败");
        }
        return Integer.valueOf(insert);
    }

    @Override
    public int addIntegral(String id, String count) {
        int row = rankingDao.addIntegral(id, count);
        return row;
    }

    @Override
    public List<Ranking> queryByName(String name) {
        return rankingDao.queryByName(name);
    }

    @Override
    public Page<RankingVO> queryGetOne(Ranking ranking, Page page) {
        List<RankingVO> list = rankingDao.queryGetOne(ranking, page);
        page.setData(list);
        return page;
    }

    @Override
    public PageBean<Ranking> findByPageToPhone(Integer pageNo, Integer pageSize) {
        int Commod = rankingDao.findByPageToPhoneCount();
        PageBean<Ranking> pager = new PageBean();
        pager.setPageSize(pageSize);
        pager.setRows(Integer.valueOf(Commod));
        pager.setPageNo(pageNo);
        int first = (pager.getPageNo().intValue() - 1) * pager.getPageSize().intValue();
        pager.setStart(Integer.valueOf(first));
        List<Ranking> list = rankingDao.findByPageToPhone(pager);
        for (Ranking r : list) {
            List<User> user = rankingDao.getRankingOfuser(r.getId());
            if (user != null) {
                r.setUsers(user);
            }
        }
        pager.setLists(list);
        return pager;
    }

    @Override
    public Ranking queryRankingDetail(Long id) {
        Ranking r = this.rankingDao.queryRankingDetail(id);
        if (r != null) {
            Integer sort = this.activityDao.querySort(r.getId());
            if (sort != null) {
                r.setSort(sort);
            }
        }
        return r;
    }

    @Override
    public PageBean<User> queryUserByRanking(Integer pageNo, Integer pageSize, Long id) {
        int Commod = this.rankingDao.queryUserByRankingCount(id);
        PageBean<User> pager = new PageBean();
        pager.setPageSize(pageSize);
        pager.setRows(Integer.valueOf(Commod));
        pager.setPageNo(pageNo);
        int first = (pager.getPageNo().intValue() - 1) * pager.getPageSize().intValue();
        pager.setStart(Integer.valueOf(first));
        List<User> list = this.rankingDao.queryUserByRanking(pager, id);
        pager.setLists(list);
        return pager;
    }

    @Override
    public RankingMoney getrankingMoney(String token) {
        CommonLoginContext contextByken = this.tokenService.getContextByken(token);
        RankingMoney rankingMoney = new RankingMoney();
        User user = userDao.findById(contextByken.getUserId());
        rankingMoney.setIntegral(user.getIntegral());
        Integer whocount = userDao.getUserWhoCount(contextByken.getUserId());
        rankingMoney.setRanking(whocount);
        List<Integer> activity = activityDao.getActivityEnd();
        int count = 0;
        if (activity != null && activity.size() > 0) {
            for (Integer i : activity) {
                String s = redisUtils.get(RedisPrefix.RANKING_USER + i.toString());
                if (s != null && s.length() > 0) {
                    String[] split = s.split(",");
                    for (String sp : split) {
                        if (sp.equals(contextByken.getUserId().toString())) {
                            count++;
                        }
                    }
                }
            }
        }
        rankingMoney.setWinRank(Integer.valueOf(count));
        return rankingMoney;
    }

    @Override
    public User getrankingMoneytouser(String token, Long id) {
        CommonLoginContext contextByken = this.tokenService.getContextByken(token);
        User byId = this.userService.findById(contextByken.getUserId());
        Integer count = this.rankingDao.getCountByActivityintegrey(contextByken.getUserId(), id);
        if (count != null) {
            byId.setRankintegral(count);
            Integer sort = this.rankingDao.getCountByActivityintegreySort(contextByken.getUserId(), id);
            byId.setSort(Integer.valueOf((sort == null) ? 0 : sort.intValue()));
        } else {
            byId.setRankintegral(Integer.valueOf(0));
            byId.setSort(Integer.valueOf(0));
        }
        return byId;
    }

    @Override
    public Integer addActor(String token, String id, String count) {
        CommonLoginContext contextByken = this.tokenService.getContextByken(token);
        User user = this.userService.findById(Long.valueOf(contextByken.getUserId().longValue()));
        System.out.println("投之前的饭团"+ user.getIntegral());
                Integer integral = user.getIntegral();
        if (integral.intValue() < Integer.parseInt(count)) {
            throw new BizException("你没有这么多的饭团");
        }
        int i1 = this.rankingDao.addIntegralForActor(id, count);
        if (i1 == 0) {
            throw new BizException("排行榜增加饭团失败");
        }
        Integral in = Integral.builder().activityId(Integer.valueOf(Integer.parseInt(id))).integralQty(Integer.valueOf(0 - Integer.parseInt(count))).type(IntegralEnum.TYPE_USE.getCode()).userId(Integer.valueOf(contextByken.getUserId().intValue())).activityName("投饭团").build();
        int insert = this.integralService.insert(in);
        if (insert == 0) {
            throw new BizException("饭团操作记录写入失败");
        }
        return Integer.valueOf(insert);
    }
}
