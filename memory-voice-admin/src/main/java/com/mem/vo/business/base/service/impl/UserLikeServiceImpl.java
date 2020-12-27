package com.mem.vo.business.base.service.impl;


import com.mem.vo.business.base.dao.UserLikeDao;
import com.mem.vo.business.base.model.po.Performance;
import com.mem.vo.business.base.model.po.PerformanceShow;
import com.mem.vo.business.base.model.po.UserLike;
import com.mem.vo.business.base.model.po.UserLikeQuery;
import com.mem.vo.business.base.model.vo.UserLikeVO;
import com.mem.vo.business.base.service.BasicPlaceService;
import com.mem.vo.business.base.service.PerformanceService;
import com.mem.vo.business.base.service.PerformanceShowService;
import com.mem.vo.business.base.service.UserLikeService;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.util.DateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

/**
*
* <br>
* <b>功能：</b>UserLikeService<br>
*/
@Service("userLikeService")
public class  UserLikeServiceImpl implements UserLikeService {
    private final static Logger log= LogManager.getLogger(UserLikeServiceImpl.class);


    @Resource
    private UserLikeDao userLikeDao;
    @Resource
    private BasicPlaceService basicPlaceService;
    @Resource
    private PerformanceShowService performanceShowService;
    @Resource
    private PerformanceService performanceService;

    @Override
    public int insert(UserLike userLike){
        return userLikeDao.insert(userLike);
    }

    @Override
    public int updateById(UserLike userLike){
        return userLikeDao.updateById(userLike);
    }

    @Override
    public int deleteById(UserLikeQuery query){
        return userLikeDao.deleteById(query);
    }

    @Override
    public UserLike findById(Long id){
        return userLikeDao.findById(id);
    }

    @Override
    public List<UserLike> findByCondition(UserLikeQuery query){
        return userLikeDao.findByCondition(query);
    }

    @Override
    public void findPageByCondition(Page page,UserLikeQuery query){
        userLikeDao.findByCondition(page,query);
    }

    /**
     * 需要优化
     * @param query
     * @return
     */
    @Override
    public List<UserLikeVO> selectAll(UserLikeQuery query) {
        List<UserLikeVO> voList = userLikeDao.selectAll(query);
        if (CollectionUtils.isEmpty(voList)) {
            return voList;
        }
        for (UserLikeVO vo : voList) {
            Performance performance = performanceService.findById(vo.getPId());
            List<Date> showTimeList = new ArrayList<>();
            if (StringUtils.isNotBlank(performance.getArtistIds())) {
                String[] showArr = performance.getShowIds().split("~");
                for (String s : showArr) {
                    PerformanceShow show = performanceShowService.findById(Long.valueOf(s));
                    if (show.getStartTime() != null) {
                        show.setStartTimeStr(DateUtil.formatDateStr(show.getStartTime()));
                    }
                    if (show.getEndTime() != null) {
                        show.setEndTimeStr(DateUtil.formatDateStr(show.getEndTime()));
                    }
                    showTimeList.add(show.getStartTime());
                }
                Collections.sort(showTimeList);
            }
            Date firstShowTime = showTimeList.get(0);
            BizAssert.notNull(firstShowTime, "获取第一场演出时间为空");
            //状态转换
            Integer status = 0;
            status = performanceService.getStatus(performance.getStartSaleDate(), firstShowTime, status);
            vo.setStatus(status);
            vo.setShowTime(firstShowTime);
            vo.setBasicPlace(basicPlaceService.findById(vo.getPlaceId()));
        }
        return voList;
    }
}
