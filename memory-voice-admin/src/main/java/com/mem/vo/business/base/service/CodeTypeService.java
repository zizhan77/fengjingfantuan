package com.mem.vo.business.base.service;
import com.mem.vo.business.base.model.po.CodeType;
import java.util.List;

public interface CodeTypeService {
    CodeType edit(CodeType paramCodeType);

    CodeType selectById(Integer paramInteger);

    List<CodeType> queryBySponsorId(String paramString);

    CodeType queryById(Integer paramInteger);

    int addCount(Integer paramInteger, int paramInt);

    int desCount(Integer paramInteger, int paramInt);

    List<CodeType> getCodeTypeForAdd(int paramInt);
}
