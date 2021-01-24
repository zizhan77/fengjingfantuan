package com.mem.vo.business.base.service.impl;


import com.mem.vo.business.base.dao.CodeTypeDao;
import com.mem.vo.business.base.model.po.CodeType;
import com.mem.vo.business.base.service.CodeTypeService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("codeTypeService")
public class CodeTypeServiceImpl implements CodeTypeService {

    @Resource
    private CodeTypeDao codeTypeDao;

    @Override
    public CodeType edit(CodeType codeType) {
        if (codeType.getId() != null && !"".equals(codeType.getId())) {
            int i = codeTypeDao.update(codeType);
            if (i == 0) {
                throw new BizException("修改失败");
            }
        } else {
            int i = codeTypeDao.insert(codeType);
            if (i == 0) {
                throw new BizException("增加失败");
            }
        }
        return codeType;
    }

    @Override
    public CodeType selectById(Integer codeType) {
        return codeTypeDao.selectById(codeType);
    }

    @Override
    public List<CodeType> queryBySponsorId(String ids) {
        String[] split = ids.split("~");
        List<String> strings = new ArrayList<>();
        for (String s : split) {
            strings.add(s);
        }
        List<CodeType> codeTypes = codeTypeDao.queryBySponsorId(strings);
        BizAssert.notEmpty(codeTypes, BizCode.BIZ_1102.getMessage());
        return codeTypes;
    }

    @Override
    public CodeType queryById(Integer codeTypeId) {
        return codeTypeDao.selectById(codeTypeId);
    }

    @Override
    public int addCount(Integer codeTypeId, int count) {
        return codeTypeDao.addCount(codeTypeId, count);
    }

    @Override
    public int desCount(Integer codeTypeId, int count) {
        return codeTypeDao.desCount(codeTypeId, count);
    }

    @Override
    public List<CodeType> getCodeTypeForAdd(int i) {
        return codeTypeDao.getCodeTypeForAdd(i);
    }
}
