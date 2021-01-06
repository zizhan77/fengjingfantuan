package com.mem.vo.business.base.service;
import com.mem.vo.business.base.model.po.Ranking;
import com.mem.vo.business.base.model.po.RankingMoney;
import com.mem.vo.business.base.model.po.User;
import com.mem.vo.business.base.model.vo.RankingVO;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.PageBean;
import java.util.List;

public interface RankingService {
    Ranking edit(Ranking ranking);

    List<Ranking> query(Ranking ranking);

    Page<RankingVO> queryPage(Ranking ranking, Page page);

    Ranking queryById(String paramString);

    Integer add(String token, String id, String count);

    int addIntegral(String id, String count);

    List<Ranking> queryByName(String name);

    Page<RankingVO> queryGetOne(Ranking ranking, Page page);

    PageBean<Ranking> findByPageToPhone(Integer pageNo, Integer pageSize);

    Ranking queryRankingDetail(Long id);

    PageBean<User> queryUserByRanking(Integer pageNo, Integer pageSize, Long id);

    RankingMoney getrankingMoney(String token);

    User getrankingMoneytouser(String token, Long id);

    Integer addActor(String paramString1, String paramString2, String paramString3);
}
