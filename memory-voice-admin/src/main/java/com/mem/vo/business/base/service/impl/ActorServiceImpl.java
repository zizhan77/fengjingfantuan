package com.mem.vo.business.base.service.impl;

import com.mem.vo.business.base.dao.ActorDao;
import com.mem.vo.business.base.model.po.Activity;
import com.mem.vo.business.base.model.po.Actor;
import com.mem.vo.business.base.model.po.ActorTirp;
import com.mem.vo.business.base.service.ActivityService;
import com.mem.vo.business.base.service.ActorServise;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.dto.PageBean;
import com.mem.vo.common.util.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ActorServiceImpl implements ActorServise {

    @Resource
    private ActorDao actorDao;

    @Resource
    private TokenService tokenService;

    @Resource
    private ActivityService activityService;

    @Override
    public PageBean<Actor> getActorList(Actor actor, Integer page, Integer pageSize) {
        int Commod = actorDao.findByPageToPhoneCount(actor);
        PageBean<Actor> pager = new PageBean();
        pager.setPageSize(pageSize);
        pager.setRows(Integer.valueOf(Commod));
        pager.setPageNo(page);
        int first = (pager.getPageNo() - 1) * pager.getPageSize();
        pager.setStart(first);
        List<Actor> list = actorDao.getActorList(actor, pager);
        pager.setLists(list);
        return pager;
    }

    @Override
    public Integer updateActor(Actor actor) {
        return actorDao.updateActor(actor);
    }

    @Override
    public Integer deleteActor(Actor actor) {
        actorDao.deleteActortrip(actor.getId());
        return actorDao.deleteActor(actor.getId());
    }

    @Override
    public Integer addActor(Actor actor) {
        return actorDao.addActor(actor);
    }

    @Override
    public PageBean<ActorTirp> getActorTripList(ActorTirp actorTirp, Integer page, Integer pageSize) {
        int Commod = actorDao.getActorTripListCount(actorTirp);
        PageBean<ActorTirp> pager = new PageBean();
        pager.setPageSize(pageSize);
        pager.setRows(Integer.valueOf(Commod));
        pager.setPageNo(page);
        int first = (pager.getPageNo().intValue() - 1) * pager.getPageSize().intValue();
        pager.setStart(Integer.valueOf(first));
        List<ActorTirp> list = actorDao.getActorTripList(actorTirp, pager);
        pager.setLists(list);
        return pager;
    }

    @Override
    public Integer updateActorTrip(ActorTirp actor) {
        return actorDao.updateActorTrip(actor);
    }

    @Override
    public Integer deleteActorTrip(ActorTirp actor) {
        return actorDao.updateActorTrip(actor);
    }

    @Override
    public Integer addActorTrip(ActorTirp actor) {
        return actorDao.deleteActorTrip(actor.getId());
    }

    @Override
    public PageBean<Actor> getPhoneActorList(Integer page, Integer pageSize) {
        int Commod = actorDao.getPhoneActorListCount();
        PageBean<Actor> pager = new PageBean();
        pager.setPageSize(pageSize);
        pager.setRows(Integer.valueOf(Commod));
        pager.setPageNo(page);
        int first = (pager.getPageNo().intValue() - 1) * pager.getPageSize().intValue();
        pager.setStart(Integer.valueOf(first));
        List<Actor> list = actorDao.getPhoneActorList(pager);
        for (Actor actor : list) {
            List<String> trip = actorDao.getActorTripNextOne(actor.getId());
            if (trip != null && trip.size() > 0) {
                actor.setNexttrip(trip.get(0));
                continue;
            }
            actor.setNexttrip("暂无行程");
        }
        pager.setLists(list);
        return pager;
    }

    @Override
    public PageBean<Actor> getActor(Integer page, Integer pageSize, String name) {
        PageBean<Actor> pager = new PageBean();
        pager.setPageSize(pageSize);
        pager.setPageNo(page);
        int first = (pager.getPageNo().intValue() - 1) * pager.getPageSize().intValue();
        pager.setStart(Integer.valueOf(first));

        List<Actor> list = actorDao.getActor(pager, name);
        for (Actor actor : list) {
            List<String> trip = actorDao.getActorTripNextOne(actor.getId());
            if (trip != null && trip.size() > 0) {
                actor.setNexttrip(trip.get(0));
                continue;
            }
            actor.setNexttrip("暂无行程");
        }
        pager.setLists(list);
        return pager;
    }

    @Override
    public PageBean<ActorTirp> getPhoneActorTripList(String name, Integer page, Integer pageSize, Integer actorid) {
        String monthFirstStr = DateUtil.getMonthFirstStr(name);
        String monthLastStr = DateUtil.getMonthLastStr(name);
        int Commod = actorDao.getPhoneActorTripListCount(monthFirstStr, monthLastStr, actorid);
        PageBean<ActorTirp> pager = new PageBean();
        pager.setPageSize(pageSize);
        pager.setRows(Integer.valueOf(Commod));
        pager.setPageNo(page);
        int first = (pager.getPageNo().intValue() - 1) * pager.getPageSize().intValue();
        pager.setStart(Integer.valueOf(first));
        List<ActorTirp> list = actorDao.getPhoneActorTripList(monthFirstStr, monthLastStr, actorid, pager);
        Integer flag = null;
        for (ActorTirp actorTirp : list) {
            if (actorTirp.getActivityid() != null) {
                Activity byId = actorDao.findByActivityId(actorTirp.getActivityid());
                if (byId != null) {
                    actorTirp.setActivityflag(Integer.valueOf(1));
                } else {
                    actorTirp.setActivityid(null);
                    actorTirp.setActivityflag(Integer.valueOf(0));
                }
                if (flag == null && byId != null) {
                    flag = actorTirp.getActivityid();
                    pager.setWincount(Integer.valueOf(Integer.parseInt(byId.getId() + "")));
                    pager.setName(byId.getActivityTitle());
                }
            }
        }
        pager.setLists(list);
        return pager;
    }

    @Override
    public Integer clickPrize(String token, Long id) {
        CommonLoginContext contextByken = tokenService.getContextByken(token);
        int i = actorDao.getCilckForall(contextByken.getUserId(), 2);
        int codeid = actorDao.selectCodeidByPrize(id);
        if (i < 0) {
            int j = actorDao.updateCodetype(codeid, 0, 1);
        } else {
            int count = actorDao.insertClick(contextByken.getUserId(), 2);
            int j = actorDao.updateCodetype(codeid, 1, 1);
        }
        return 1;
    }

    @Override
    public Integer clickBanner(String token, Long id) {
        if (token != null && token != "") {
            CommonLoginContext contextByken = tokenService.getContextByken(token);
            int i = actorDao.getCilckForall(contextByken.getUserId(), 0);
            if (i < 0) {
                int j = actorDao.updateBanner(id, 0, 1);
            } else {
                int count = actorDao.insertClick(contextByken.getUserId(), 0);
                int j = actorDao.updateBanner(id, 1, 1);
            }
        } else {
            int i = actorDao.updateBanner(id, 0, 1);
        }
        return 1;
}

    @Override
    public Integer clickSponsor(String token, Long id) {
        if (token != null && token != "") {
            CommonLoginContext contextByken = tokenService.getContextByken(token);
            int i = actorDao.getCilckForall(contextByken.getUserId(), 1);
            if (i < 0) {
                int j = actorDao.clickSponsor(id, 0, 1);
            } else {
                int count = actorDao.insertClick(contextByken.getUserId(), 1);
                int j = actorDao.clickSponsor(id, 1, 1);
            }
        } else {
            int i = actorDao.clickSponsor(id, 0, 1);
        }
        return 1;
    }
}
