package com.mem.vo.business.base.service.impl;


import com.mem.vo.business.base.dao.ExchangeCodeMainDao;
import com.mem.vo.business.base.model.po.*;
import com.mem.vo.business.base.service.ExchangeCodeMainService;
import com.mem.vo.business.base.service.ExchangeCodeRecordService;
import com.mem.vo.business.base.service.UserService;
import com.mem.vo.business.biz.model.vo.exchangecode.ExchangeCodeRequest;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.ExRecordStatusEnum;
import com.mem.vo.common.constant.ExchangeCodeBizType;
import com.mem.vo.common.constant.ExchangeCodeStatusEnum;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.util.BeanCopyUtil;
import com.mem.vo.common.util.ExchangeCodeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
*
* <br>
* <b>功能：</b>ExchangeCodeMainService<br>
*/
@Service("exchangeCodeMainService")
public class  ExchangeCodeMainServiceImpl implements ExchangeCodeMainService {
    private final static Logger log= LogManager.getLogger(ExchangeCodeMainServiceImpl.class);


    @Resource
    private ExchangeCodeMainDao exchangeCodeMainDao;

    @Resource
    private TokenService tokenService;

    @Resource
    private ExchangeCodeRecordService exchangeCodeRecordService;

    @Resource
    private UserService userService;

    @Override
    public int insert(ExchangeCodeMain exchangeCodeMain){
        return exchangeCodeMainDao.insert(exchangeCodeMain);
    }

    @Override
    public int updateById(ExchangeCodeMain exchangeCodeMain){
        return exchangeCodeMainDao.updateById(exchangeCodeMain);
    }

    @Override
    public int deleteById(Long id){
        return exchangeCodeMainDao.deleteById(id);
    }

    @Override
    public ExchangeCodeMain findById(Long id){
        return exchangeCodeMainDao.findById(id);
    }

    @Override
    public List<ExchangeCodeMain> findByCondition(ExchangeCodeMainQuery query){
        return exchangeCodeMainDao.findByCondition(query);
    }

    @Override
    public Page<ExchangeCodeMain> findPageByCondition(Page page, ExchangeCodeMainQuery query){
        List<ExchangeCodeMain> list = exchangeCodeMainDao.findByCondition(page,query);
        page.setData(list);
        return page;
    }

    @Override
    @Transactional
    public void testTrans() {
        ExchangeCodeMain exchangeCodeMain = new ExchangeCodeMain();
        exchangeCodeMain.setBusinessKey("test1111");
        exchangeCodeMainDao.insert(exchangeCodeMain);

        //

        ExchangeCodeMain exchangeCodeMain1 = new ExchangeCodeMain();
        exchangeCodeMain1.setBusinessKey("test2222");
        exchangeCodeMainDao.insert(exchangeCodeMain);

        int a = 2/0;

    }

    @Override
    public List<String> generateCommonExchangeCode(Long userId, ExchangeCodeRequest exchangeCodeRequest) {
        List<String> exchangeCodeList  =  new ArrayList<>();

        BizAssert.notNull(exchangeCodeRequest.getBusinessTag(),"业务来源标识为空");
        BizAssert.notNull(exchangeCodeRequest.getBusinessKey(),"业务外键key为空");
        BizAssert.notNull(exchangeCodeRequest.getNumber(),"生成数量为空");
        BizAssert.notEmpty(exchangeCodeRequest.getRecordBusinessKeyList(),"明细主键为空");
        BizAssert.isTrue(exchangeCodeRequest.getRecordBusinessKeyList().size()==exchangeCodeRequest.getNumber(),"生成数量与明细条数不相等" );

        BizAssert.notNull(userId,"获取用户id 失败");
        User user = userService.findById(userId);
        BizAssert.notNull(user,"查询当前用户为空");
        BizAssert.hasText(user.getPhoneNumber(),"用户手机号为空");


        ExchangeCodeMain exchangeCodeMain  =new ExchangeCodeMain();
        exchangeCodeMain.setBusinessTag(exchangeCodeRequest.getBusinessTag());
        exchangeCodeMain.setBusinessKey(exchangeCodeRequest.getBusinessKey());
        exchangeCodeMain.setAllNum(exchangeCodeRequest.getNumber());
        exchangeCodeMain.setChangedNum(0);
        exchangeCodeMain.setUsedNum(0);
        exchangeCodeMain.setStatus(ExchangeCodeStatusEnum.OFF.getCode());
        exchangeCodeMain.setRemark(exchangeCodeRequest.getRemark());


        insert(exchangeCodeMain);

        for (String recordbusinessKey:exchangeCodeRequest.getRecordBusinessKeyList()) {
            ExchangeCodeRecord exchangeCodeRecord = new ExchangeCodeRecord();
            exchangeCodeRecord.setBusinessTag(exchangeCodeRequest.getBusinessTag());
            //StringBuilder stringBuilder =new StringBuilder("EC-").append(exchangeCodeRequest.getBusinessTag()).append("-").append(System.currentTimeMillis());

            exchangeCodeRecord.setExchangeCode(ExchangeCodeUtil.getStringRandom(6));
            exchangeCodeRecord.setRemark(exchangeCodeRequest.getRemark());
            exchangeCodeRecord.setBusinessKey(recordbusinessKey);

            exchangeCodeRecord.setUserPhone(user.getPhoneNumber());
            exchangeCodeRecord.setMainId(exchangeCodeMain.getId());
            if(ExchangeCodeBizType.FOR_TICKET.getCode().equals(exchangeCodeRequest.getBusinessKey())){
                exchangeCodeRecord.setStatus(ExRecordStatusEnum.UN_USE.getCode());
            }else{
                exchangeCodeRecord.setStatus(ExRecordStatusEnum.UN_EXCHANGE.getCode());
            }
            ExchangeCodeRecordQuery query = BeanCopyUtil.copyProperties(exchangeCodeRecord,ExchangeCodeRecordQuery.class);
            while (CollectionUtils.isNotEmpty(exchangeCodeRecordService.findByCondition(query))){
                String exchangeCode =  ExchangeCodeUtil.getStringRandom(6);
                exchangeCodeRecord.setExchangeCode(exchangeCode);
                query.setExchangeCode(exchangeCode);
            }
            exchangeCodeRecordService.insert(exchangeCodeRecord);
            exchangeCodeList.add(exchangeCodeRecord.getExchangeCode());
        }

        return exchangeCodeList;
    }


    @Override
    public int updateRealUserByTagAndKey(String businessTag, List<String> recordbusinessKeyList, Long userId) {
        return 0;
    }
}
