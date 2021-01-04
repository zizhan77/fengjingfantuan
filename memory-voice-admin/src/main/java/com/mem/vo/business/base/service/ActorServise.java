package com.mem.vo.business.base.service;


import com.mem.vo.business.base.model.po.Actor;
import com.mem.vo.business.base.model.po.ActorTirp;
import com.mem.vo.common.dto.PageBean;

public interface ActorServise {
    PageBean<Actor> getActorList(Actor paramActor, Integer paramInteger1, Integer paramInteger2);

    Integer updateActor(Actor paramActor);

    Integer deleteActor(Actor paramActor);

    Integer addActor(Actor paramActor);

    PageBean<ActorTirp> getActorTripList(ActorTirp paramActorTirp, Integer paramInteger1, Integer paramInteger2);

    Integer updateActorTrip(ActorTirp paramActorTirp);

    Integer deleteActorTrip(ActorTirp paramActorTirp);

    Integer addActorTrip(ActorTirp paramActorTirp);

    PageBean<Actor> getPhoneActorList(Integer paramInteger1, Integer paramInteger2);

    PageBean<ActorTirp> getPhoneActorTripList(String paramString, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3);

    Integer clickPrize(String paramString, Long paramLong);

    Integer clickBanner(String paramString, Long paramLong);

    Integer clickSponsor(String paramString, Long paramLong);
}
