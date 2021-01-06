package com.mem.vo.business.base.service;
import com.mem.vo.business.base.model.po.CodeType;
import java.util.List;

public interface CodeTypeService {
    CodeType edit(CodeType codeType);

    CodeType selectById(Integer codeType);

    List<CodeType> queryBySponsorId(String ids);

    CodeType queryById(Integer codeTypeId);

    int addCount(Integer codeTypeId, int count);

    int desCount(Integer codeTypeId, int count);

    List<CodeType> getCodeTypeForAdd(int i);
}
