package com.mem.vo.business.base.service.impl;


import com.mem.vo.business.base.dao.SeatsDao;
import com.mem.vo.business.base.model.po.Seats;
import com.mem.vo.business.base.model.po.SeatsQuery;
import com.mem.vo.business.base.service.SeatsService;
import com.mem.vo.common.dto.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <br>
 * <b>功能：</b>SeatsService<br>
 */
@Service("seatsService")
public class SeatsServiceImpl implements SeatsService {

    private final static Logger log = LogManager.getLogger(SeatsServiceImpl.class);


    @Resource
    private SeatsDao seatsDao;


    //初始化演出场次座位
/*    public ResponseDto<String> initShowSeatList(@RequestHeader("token") String token , String seatsListJson) {
        ResponseDto<String> responseDto = ResponseDto.successDto();
        try {
            List<Seats> seatsList= JSON.parseArray(seatsListJson,Seats.class);
            if(seatsList.size()<=0){
                throw new BizException(BizCode.PARAM_NULL.getMessage());
            }
            for(Seats s:seatsList){
                if(seatsService.insert(s)<=0)
                    throw new BizException(BizCode.BIZ_1035.getMessage());
            }
            return responseDto.successData("新增成功");
        } catch (BizException e) {
            log.error("更改选座结果异常,原因：{}",  e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("更改选座结果异常",  e);
            return responseDto.failSys();
        }
    }*/
    @Override
    public int insert(Seats seats) {
        return seatsDao.insert(seats);
    }

    @Override
    public int updateById(Seats seats) {
        return seatsDao.updateById(seats);
    }

    @Override
    public int updateByIds(List<Seats> seatsList) {
        return seatsDao.updateByIds(seatsList);
    }

    @Override
    public int deleteById(Long id) {
        return seatsDao.deleteById(id);
    }

    @Override
    public Seats findById(Long id) {
        return seatsDao.findById(id);
    }

    @Override
    public List<Seats> findByIds(String ids) {
        return seatsDao.findByIds(ids);
    }

    @Override
    public List<Seats> findByCondition(SeatsQuery query) {
        return seatsDao.findByCondition(query);
    }

    @Override
    public Page<Seats> findPageByCondition(Page page, SeatsQuery query) {
        List<Seats> seats = seatsDao.findByCondition(page, query);
        page.setData(seats);
        return page;
    }
}
