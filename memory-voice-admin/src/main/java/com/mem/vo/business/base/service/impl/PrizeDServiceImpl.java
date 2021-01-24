package com.mem.vo.business.base.service.impl;


import com.mem.vo.business.base.dao.PrizeDDao;
import com.mem.vo.business.base.dao.PrizeDao;
import com.mem.vo.business.base.model.po.PrizeD;
import com.mem.vo.business.base.model.po.PrizeDQuery;
import com.mem.vo.business.base.service.*;
import com.mem.vo.common.constant.IsDeleteEnum;
import com.mem.vo.common.constant.PrizeEnum;
import com.mem.vo.common.constant.RewardEnum;
import com.mem.vo.common.dto.Page;

import java.util.List;

import com.mem.vo.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
*
* <br>
* <b>功能：</b>PrizeDService<br>
*/
@Service("prizeDService")
@Slf4j
public class  PrizeDServiceImpl implements PrizeDService {


    @Resource
    private PrizeService prizeService;

    @Resource
    private PrizeDao prizeDao;

    @Resource
    private RewardService rewardService;

    @Resource
    private ChangeCodeService changeCodeService;

    @Resource
    private PrizeDDao prizeDDao;

    @Resource
    private PrizeDService prizeDService;

    @Resource
    private CodeTypeService codeTypeService;

    @Override
    public int insert(PrizeD prizeD){
        return prizeDDao.insert(prizeD);
    }

    @Override
    public int insertBatch(List<PrizeD> prizeDQuery) {
        return prizeDDao.insertBatch(prizeDQuery);
    }

    @Override
    public int updateById(PrizeD prizeD){
        return prizeDDao.updateById(prizeD);
    }

    @Override
    public int deleteById(List<Long> ids){
        return prizeDDao.deleteById(ids);
    }

    @Override
    public PrizeD findById(Long id){
        return prizeDDao.findById(id);
    }

    @Override
    public List<PrizeD> findByCondition(PrizeDQuery query){
        return prizeDDao.findByCondition(query);
    }

    @Override
    public void findPageByCondition(Page page,PrizeDQuery query){
        prizeDDao.findByCondition(page,query);
    }

    @Override
    public List<Integer> selectIdByEt(PrizeDQuery query) {
        return prizeDDao.selectIdByEt(query);
    }

    @Override
    public List<PrizeD> findByLevelAndActivityIdAndType(PrizeDQuery query) {
        return prizeDDao.findByLevelAndActivityIdAndType(query);
    }

    @Override
    public int deleteByPrizeId(Integer prizeId) {
        return prizeDDao.deleteByPrizeId(prizeId);
    }

    @Override
    @Transactional
    public Integer giveUp(String prizedId) {
        PrizeD prized = prizeDDao.findById(Long.valueOf(prizedId));
        int i = rewardService.updateByPrizedId(prized.getId(), RewardEnum.STATUS_GIVEUP.getCode());
        if (i == 0) {
            throw new BizException("放弃奖品，修改中奖记录失败");
        }
        if (prized.getPrizeType().equals(PrizeEnum.coupon.getCode())) {
            Integer keyId = prized.getKeyId();
            changeCodeService.updateById(keyId, IsDeleteEnum.YES.getCode());
            prized.setKeyId(Integer.valueOf(-1));
        }
        prized.setIsChange(PrizeEnum.NO_CHANGE.getCode());
        int i1 = prizeDDao.updateById(prized);
        if (i1 == 0) {
            throw new BizException("放弃奖品，修改奖品详情失败");
        }
        Integer prizeId = prized.getPrizeId();
        int i2 = prizeService.rollBackStoreById(prizeId);
        if (i2 == 0) {
            throw new BizException("放弃奖品，修改奖品库存失败");
        }
        return Integer.valueOf(i2);
    }
}
