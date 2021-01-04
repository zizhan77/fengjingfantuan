package com.mem.vo.business.base.service;


import com.mem.vo.business.base.model.po.Actor;
import com.mem.vo.business.base.model.po.ActorTirp;
import com.mem.vo.common.dto.PageBean;

public interface ActorServise {
    PageBean<Actor> getActorList(Actor actor, Integer page, Integer pageSize);

    Integer updateActor(Actor actor);

    Integer deleteActor(Actor actor);

    Integer addActor(Actor actor);

    PageBean<ActorTirp> getActorTripList(ActorTirp actorTirp, Integer page, Integer pageSize);

    Integer updateActorTrip(ActorTirp actor);

    Integer deleteActorTrip(ActorTirp actor);

    Integer addActorTrip(ActorTirp actor);

    PageBean<Actor> getPhoneActorList(Integer page, Integer pageSize);

    PageBean<ActorTirp> getPhoneActorTripList(String name, Integer page, Integer pageSize, Integer actorid);

    Integer clickPrize(String token, Long id);

    Integer clickBanner(String token, Long id);

    Integer clickSponsor(String token, Long id);
}
