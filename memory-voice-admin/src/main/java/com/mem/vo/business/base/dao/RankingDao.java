package com.mem.vo.business.base.dao;

import com.mem.vo.business.base.model.po.Ranking;
import com.mem.vo.business.base.model.po.User;
import com.mem.vo.business.base.model.vo.RankingVO;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.PageBean;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface RankingDao {
    int insert(Ranking ranking);

    int update(Ranking ranking);

    List<Ranking> query(Ranking ranking);

    List<RankingVO> queryPage(@Param("ranking") Ranking ranking, @Param("page") Page page);

    List<Ranking> queryByName(@Param("name") String name);

    @Update({"update ranking set count = count + #{count} where id = #{id}"})
    int addIntegral(@Param("id") String id, @Param("count") String count);

    List<RankingVO> queryGetOne(@Param("ranking") Ranking ranking, @Param("page") Page page);

    int findByPageToPhoneCount();

    List<Ranking> findByPageToPhone(@Param("paging") PageBean<Ranking> paging);

    List<User> getRankingOfuser(@Param("id") Long id);

    Ranking queryRankingDetail(@Param("id") Long id);

    int queryUserByRankingCount(@Param("id") Long id);

    List<User> queryUserByRanking(@Param("paging") PageBean<User> paging, @Param("id") Long id);

    Integer getCountByActivityintegrey(@Param("userid") Long paging, @Param("id") Long id);

    Integer getCountByActivityintegreySort(@Param("userid") Long paging, @Param("id") Long id);

    int deleteById(Long paramLong);

    @Update({"update actor set integral = integral + #{count} where id = #{id}"})
    int addIntegralForActor(@Param("id") String id, @Param("count") String count);
}
