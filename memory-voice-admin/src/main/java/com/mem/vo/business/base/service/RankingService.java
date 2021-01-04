package com.mem.vo.business.base.service;
import com.mem.vo.business.base.model.po.Ranking;
import com.mem.vo.business.base.model.po.RankingMoney;
import com.mem.vo.business.base.model.po.User;
import com.mem.vo.business.base.model.vo.RankingVO;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.PageBean;
import java.util.List;

public interface RankingService {
    Ranking edit(Ranking paramRanking);

    List<Ranking> query(Ranking paramRanking);

    Page<RankingVO> queryPage(Ranking paramRanking, Page paramPage);

    Ranking queryById(String paramString);

    Integer add(String paramString1, String paramString2, String paramString3);

    int addIntegral(String paramString1, String paramString2);

    List<Ranking> queryByName(String paramString);

    Page<RankingVO> queryGetOne(Ranking paramRanking, Page paramPage);

    PageBean<Ranking> findByPageToPhone(Integer paramInteger1, Integer paramInteger2);

    Ranking queryRankingDetail(Long paramLong);

    PageBean<User> queryUserByRanking(Integer paramInteger1, Integer paramInteger2, Long paramLong);

    RankingMoney getrankingMoney(String paramString);

    User getrankingMoneytouser(String paramString, Long paramLong);

    Integer addActor(String paramString1, String paramString2, String paramString3);
}
