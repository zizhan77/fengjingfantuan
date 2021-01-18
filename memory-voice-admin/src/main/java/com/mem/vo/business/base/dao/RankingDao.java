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
    int insert(Ranking paramRanking);

    int update(Ranking paramRanking);

    List<Ranking> query(Ranking paramRanking);

    List<RankingVO> queryPage(@Param("ranking") Ranking paramRanking, @Param("page") Page paramPage);

    List<Ranking> queryByName(@Param("name") String paramString);

    @Update({"update ranking set count = count + #{count} where id = #{id}"})
    int addIntegral(@Param("id") String id, @Param("count") String count);

    List<RankingVO> queryGetOne(@Param("ranking") Ranking ranking, @Param("page") Page page);

    int findByPageToPhoneCount();

    List<Ranking> findByPageToPhone(@Param("paging") PageBean<Ranking> paramPageBean);

    List<User> getRankingOfuser(@Param("id") Long paramLong);

    Ranking queryRankingDetail(@Param("id") Long paramLong);

    int queryUserByRankingCount(@Param("id") Long paramLong);

    List<User> queryUserByRanking(@Param("paging") PageBean<User> paramPageBean, @Param("id") Long paramLong);

    Integer getCountByActivityintegrey(@Param("userid") Long paramLong1, @Param("id") Long paramLong2);

    Integer getCountByActivityintegreySort(@Param("userid") Long paramLong1, @Param("id") Long paramLong2);

    int deleteById(Long paramLong);

    @Update({"update actor set integral = integral + #{count} where id = #{id}"})
    int addIntegralForActor(@Param("id") String id, @Param("count") String count);
}
