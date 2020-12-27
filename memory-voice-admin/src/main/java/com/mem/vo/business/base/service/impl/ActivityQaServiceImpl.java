package com.mem.vo.business.base.service.impl;


import com.mem.vo.business.base.dao.ActivityQaDao;
import com.mem.vo.business.base.model.po.ActivityQa;
import com.mem.vo.business.base.model.po.ActivityQaQuery;
import com.mem.vo.business.base.service.ActivityQaService;
import com.mem.vo.common.dto.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.annotation.Resource;
import java.util.List;
import org.springframework.stereotype.Service;

/**
*
* <br>
* <b>功能：</b>ActivityQaService<br>
*/
@Service("activityQaService")
public class  ActivityQaServiceImpl implements ActivityQaService {
    private final static Logger log= LogManager.getLogger(ActivityQaServiceImpl.class);


    @Resource
    private ActivityQaDao activityQaDao;

    @Override
    public int insert(ActivityQa activityQa){
        return activityQaDao.insert(activityQa);
    }

    @Override
    public int updateById(ActivityQa activityQa){
        return activityQaDao.updateById(activityQa);
    }

    @Override
    public int deleteById(Long id){
        return activityQaDao.deleteById(id);
    }

    @Override
    public ActivityQa findById(Long id){
        return activityQaDao.findById(id);
    }

    @Override
    public List<ActivityQa> findByCondition(ActivityQaQuery query){
        return activityQaDao.findByCondition(query);
    }

    @Override
    public void findPageByCondition(Page page, ActivityQaQuery query){
        activityQaDao.findByCondition(page,query);
    }
}
