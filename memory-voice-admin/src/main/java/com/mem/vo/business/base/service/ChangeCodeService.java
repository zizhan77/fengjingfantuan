package com.mem.vo.business.base.service;

import com.mem.vo.business.base.model.po.ChangeCode;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ChangeCodeService {
    int updateById(Integer id, Integer code);

    List<ChangeCode> selectByChangeCodeId(Integer codeType, Integer code);

    ChangeCode selectById(Integer keyId);

    List<ChangeCode> selectByCodeTypeId(Integer codeType, Integer code);

    Integer up(String codeTypeId, MultipartFile file);
}
