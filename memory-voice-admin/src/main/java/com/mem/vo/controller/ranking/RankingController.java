package com.mem.vo.controller.ranking;

import com.mem.vo.business.base.model.po.Ranking;
import com.mem.vo.business.base.model.po.RankingMoney;
import com.mem.vo.business.base.model.po.User;
import com.mem.vo.business.base.model.vo.RankingVO;
import com.mem.vo.business.base.service.RankingService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.PageBean;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/ranking"})
public class RankingController {
    private static final Logger log = LoggerFactory.getLogger(RankingController.class);

    @Resource
    private RankingService rankingService;

    @PostMapping({"/phone/getRankingtToUserIntegrty"})
    public ResponseDto<User> getrankingMoneytouser(@RequestHeader("token") String token, Long id) {
        ResponseDto<User> responseDto = ResponseDto.successDto();
        User user = rankingService.getrankingMoneytouser(token, id);
        responseDto.setData(user);
        return responseDto;
    }

    @PostMapping({"/phone/getRankingMoney"})
    public ResponseDto<RankingMoney> getrankingMoney(@RequestHeader("token") String token) {
        ResponseDto<RankingMoney> responseDto = ResponseDto.successDto();
        RankingMoney rankingMoney = rankingService.getrankingMoney(token);
        responseDto.setData(rankingMoney);
        return responseDto;
    }

    @PostMapping({"/phone/queryUserByRanking"})
    public ResponseDto<PageBean<User>> queryUserByRanking(@RequestHeader("token") String token, @RequestParam(required = true) Integer pageNo, @RequestParam(required = true) Integer pageSize, @RequestParam(required = true) Long id) {
        ResponseDto<PageBean<User>> responseDto = ResponseDto.successDto();
        PageBean<User> byPage = rankingService.queryUserByRanking(pageNo, pageSize, id);
        return responseDto.successData(byPage);
    }

    @PostMapping({"/phone/queryRankingDetail"})
    public ResponseDto<Ranking> queryRankingDetail(Long id) {
        ResponseDto<Ranking> responseDto = ResponseDto.successDto();
        Ranking byPage = rankingService.queryRankingDetail(id);
        return responseDto.successData(byPage);
    }

    @PostMapping({"/phone/queryAllByPhone"})
    public ResponseDto<PageBean<Ranking>> queryAllByPhone(@RequestParam(required = true) Integer pageNo, @RequestParam(required = true) Integer pageSize) {
        ResponseDto<PageBean<Ranking>> responseDto = ResponseDto.successDto();
        PageBean<Ranking> byPage = rankingService.findByPageToPhone(pageNo, pageSize);
        return responseDto.successData(byPage);
    }

    @PostMapping({"/edit"})
    public ResponseDto<Ranking> edit(Ranking ranking) {
        ResponseDto<Ranking> responseDto = ResponseDto.successDto();
        try {
            BizAssert.notNull(ranking, BizCode.PARAM_NULL.getMessage());
            return responseDto.successData(rankingService.edit(ranking));
        } catch (BizException e) {
            log.error("增加排行项目异常, 原因:{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("增加排行项目异常", e);
            return responseDto.failSys();
        }
    }

    @PostMapping({"/query"})
    public ResponseDto query(@RequestHeader("token") String token, String rankingId) {
        ResponseDto<Ranking> responseDto = ResponseDto.successDto();
        try {
            return responseDto.successData(this.rankingService.queryById(rankingId));
        } catch (BizException e) {
            log.error("查询排行项目异常, 原因:{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("查询排行项目异常", e);
            return responseDto.failSys();
        }
    }

    @PostMapping({"/queryGetOne"})
    public ResponseDto<Page<RankingVO>> queryGetOne(Ranking ranking, Page page) {
        ResponseDto<Page<RankingVO>> responseDto = ResponseDto.successDto();
        try {
            return responseDto.successData(this.rankingService.queryGetOne(ranking, page));
        } catch (BizException e) {
            log.error("查询排行项目异常, 原因:{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("查询排行项目异常", e);
            return responseDto.failSys();
        }
    }

    @PostMapping({"/queryPage"})
    public ResponseDto<Page<RankingVO>> queryPage(Ranking ranking, Page page) {
        ResponseDto<Page<RankingVO>> responseDto = ResponseDto.successDto();
        try {
            return responseDto.successData(this.rankingService.queryPage(ranking, page));
        } catch (BizException e) {
            log.error("查询排行项目异常, 原因:{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("查询排行项目异常", e);
            return responseDto.failSys();
        }
    }

    @PostMapping({"/addActor"})
    public ResponseDto<Integer> addActor(@RequestHeader("token") String token, String id, String count) {
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        try {
            if (id == null || "".equals(id)) {
                throw new BizException(BizCode.PARAM_NULL.getMessage());
            }
            if (count == null || "".equals(count)) {
                throw new BizException(BizCode.PARAM_NULL.getMessage());
            }
            return responseDto.successData(this.rankingService.addActor(token, id, count));
        } catch (BizException e) {
            log.error("查询排行项目异常, 原因:{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("查询排行项目异常", e);
            return responseDto.failSys();
        }
    }

    @PostMapping({"/add"})
    public ResponseDto<Integer> queryPage(@RequestHeader("token") String token, String id, String count) {
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        try {
            if (id == null || "".equals(id)) {
                throw new BizException(BizCode.PARAM_NULL.getMessage());
            }
            if (count == null || "".equals(count)) {
                throw new BizException(BizCode.PARAM_NULL.getMessage());
            }
            return responseDto.successData(this.rankingService.add(token, id, count));
        } catch (BizException e) {
            log.error("查询排行项目异常, 原因:{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("查询排行项目异常", e);
            return responseDto.failSys();
        }
    }

    @PostMapping({"/queryByName"})
    public ResponseDto<List<Ranking>> queryByName(String name) {
        ResponseDto<List<Ranking>> responseDto = ResponseDto.successDto();
        try {
            return responseDto.successData(this.rankingService.queryByName(name));
        } catch (BizException e) {
            log.error("查询排行项目异常, 原因:{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("查询排行项目异常", e);
            return responseDto.failSys();
        }
    }
}
