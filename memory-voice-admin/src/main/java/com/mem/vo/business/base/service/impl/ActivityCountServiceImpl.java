package com.mem.vo.business.base.service.impl;

import com.mem.vo.business.base.dao.ActivitycountMapper;
import com.mem.vo.business.base.model.po.Activitycount;
import com.mem.vo.business.base.model.vo.ActivitycountVO;
import com.mem.vo.business.base.service.ActivityCountService;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.DateUtil;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ActivityCountServiceImpl implements ActivityCountService{
    @Resource
    private ActivitycountMapper activitycountMapper;

    @Resource
    private TokenService tokenService;

    @Override
    public void a() {
        for (int i = 0; i < 4000; i++) {
            this.activitycountMapper.updateabcd();
        }
    }

    @Override
    public String add(String token, String activityId) {
        BizAssert.notNull(token, BizCode.PARAM_NULL.getMessage());
        BizAssert.notNull(activityId, BizCode.PARAM_NULL.getMessage());
        CommonLoginContext contextByken = this.tokenService.getContextByken(token);
        Long userId = contextByken.getUserId();
        Activitycount activitycount = new Activitycount();
        activitycount.setActivityId(activityId);
        activitycount.setUserId(userId.toString());
        String nowStr = DateUtil.getNowStr();
        activitycount.setDate(nowStr);
        List<Activitycount> list = this.activitycountMapper.selectBy(activitycount);
        if (!list.isEmpty()) {
            int a = this.activitycountMapper.update(activitycount);
            if (a == 0) {
                throw new BizException("增加统计失败次数");
            }
            return a + "";
        }
        activitycount.setCount("1");
        int b = this.activitycountMapper.insert(activitycount);
        if (b == 0) {
            throw new BizException("正价统计记录失败");
        }
        return b + "";
    }

    @Override
    public List<ActivitycountVO> query(String date, Activitycount activitycount) {
        BizAssert.notNull(date, BizCode.PARAM_NULL.getMessage());
        List<ActivitycountVO> list = this.activitycountMapper.query(date, activitycount);
        return list;
    }

    @Override
    public Page<ActivitycountVO> queryByName(Page<ActivitycountVO> page, String activityName) {
        BizAssert.notNull(activityName, BizCode.PARAM_NULL.getMessage());
        List<ActivitycountVO> list = this.activitycountMapper.queryByName(page, activityName);
        page.setData(list);
        return page;
    }
}
