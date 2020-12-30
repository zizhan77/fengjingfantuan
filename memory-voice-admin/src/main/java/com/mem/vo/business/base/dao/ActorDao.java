package com.mem.vo.business.base.dao;

import com.mem.vo.business.base.model.po.Activity;
import com.mem.vo.business.base.model.po.Actor;
import com.mem.vo.business.base.model.po.ActorTirp;
import com.mem.vo.common.dto.PageBean;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ActorDao {
    List<Actor> getActorList(@Param("a") Actor paramActor, @Param("p") PageBean<Actor> paramPageBean);

    Integer updateActor(Actor paramActor);

    void deleteActortrip(Integer paramInteger);

    Integer deleteActor(Integer paramInteger);

    int findByPageToPhoneCount(Actor paramActor);

    Integer addActor(Actor paramActor);

    int getActorTripListCount(ActorTirp paramActorTirp);

    List<ActorTirp> getActorTripList(@Param("a") ActorTirp paramActorTirp, @Param("p") PageBean<ActorTirp> paramPageBean);

    Integer updateActorTrip(ActorTirp paramActorTirp);

    Integer deleteActorTrip(Integer paramInteger);

    Integer addActorTrip(ActorTirp paramActorTirp);

    int getPhoneActorListCount();

    List<Actor> getPhoneActorList(@Param("p") PageBean<Actor> paramPageBean);

    List<String> getActorTripNextOne(Integer paramInteger);

    int getPhoneActorTripListCount(@Param("first") String paramString1, @Param("last") String paramString2, @Param("id") Integer paramInteger);

    List<ActorTirp> getPhoneActorTripList(@Param("first") String paramString1, @Param("last") String paramString2, @Param("id") Integer paramInteger, @Param("p") PageBean<ActorTirp> paramPageBean);

    int getCilckForall(@Param("userid") Long paramLong, @Param("from") int paramInt);

    int insertClick(@Param("userid") Long paramLong, @Param("from") int paramInt);

    int updateCodetype(@Param("id") int paramInt1, @Param("people") int paramInt2, @Param("clicknum") int paramInt3);

    int selectCodeidByPrize(Long paramLong);

    int updateBanner(@Param("id") Long paramLong, @Param("people") int paramInt1, @Param("clicknum") int paramInt2);

    int clickSponsor(@Param("id") Long paramLong, @Param("people") int paramInt1, @Param("clicknum") int paramInt2);

    Activity findByActivityId(Integer paramInteger);
}
