package com.mem.vo.business.base.service;

import com.mem.vo.business.base.model.po.ChangeCode;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ChangeCodeService {
    int updateById(Integer paramInteger1, Integer paramInteger2);

    List<ChangeCode> selectByChangeCodeId(Integer paramInteger1, Integer paramInteger2);

    ChangeCode selectById(Integer paramInteger);

    List<ChangeCode> selectByCodeTypeId(Integer paramInteger1, Integer paramInteger2);

    Integer up(String paramString, MultipartFile paramMultipartFile);
}
