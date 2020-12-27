package com.mem.vo.business.base.service.impl;


import com.mem.vo.business.base.dao.ShowSeatsDao;
import com.mem.vo.business.base.model.po.ShowSeats;
import com.mem.vo.business.base.model.po.ShowSeatsQuery;
import com.mem.vo.business.base.service.ShowSeatsService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.RedisUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <br>
 * <b>功能：</b>ShowSeatsService<br>
 */
@Service("showSeatsService")
public class ShowSeatsServiceImpl implements ShowSeatsService {

    private final static Logger log = LogManager.getLogger(ShowSeatsServiceImpl.class);


    @Resource
    private ShowSeatsDao showSeatsDao;
    @Resource
    private RedisUtils redisUtils;

    @Override
    @Transactional
    public Integer saveSeat(ShowSeats showSeats){
        try {
            ShowSeats querySeat = showSeatsDao.findById(showSeats.getId());
            if (querySeat.getStatus().equals(1)
                    && redisUtils.setnx(querySeat.getShowId()+"_"+querySeat.getId(),
                    "1",2000)){
                log.info("获得redis锁");
            }else{
                throw new BizException(BizCode.BIZ_1030.getMessage());
            }
            int successNum = showSeatsDao.updateById(showSeats);
            log.info("successNum:"+successNum);
            if(successNum <= 0){
                throw new BizException(BizCode.BIZ_1030.getMessage());
            }

            return successNum;
        } catch (BizException e) {
            log.error("保存选座结果异常,原因：{}",  e.getMessage());
            return 0;
        } catch (Exception e) {
            log.error("保存选座结果异常",  e);
            return 0;

        }
    }

    @Override
    public int insert(ShowSeats showSeats) {
        return showSeatsDao.insert(showSeats);
    }

    @Override
    public int updateById(ShowSeats showSeats) {
        return showSeatsDao.updateById(showSeats);
    }

    @Override
    public int deleteById(Long id) {
        return showSeatsDao.deleteById(id);
    }

    @Override
    public ShowSeats findById(Long id) {
        return showSeatsDao.findById(id);
    }

    @Override
    public List<ShowSeats> findByCondition(ShowSeatsQuery query) {
        return showSeatsDao.findByCondition(query);
    }

    @Override
    public Page<ShowSeats> findPageByCondition(Page page, ShowSeatsQuery query) {
        List<ShowSeats> list = showSeatsDao.findByCondition(page, query);
        page.setData(list);
        return page;
    }
}
