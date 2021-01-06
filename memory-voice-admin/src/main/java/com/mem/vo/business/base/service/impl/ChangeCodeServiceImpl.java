package com.mem.vo.business.base.service.impl;

import com.mem.vo.business.base.dao.ChangeCodeDao;
import com.mem.vo.business.base.dao.CodeTypeDao;
import com.mem.vo.business.base.model.po.ChangeCode;
import com.mem.vo.business.base.model.vo.ExcelDataVO;
import com.mem.vo.business.base.service.ChangeCodeService;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.ExcelUtil;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("changeCodeService")
public class ChangeCodeServiceImpl implements ChangeCodeService {

    @Resource
    private TokenService tokenService;

    @Resource
    private ChangeCodeDao changeCodeDao;

    @Resource
    private CodeTypeDao codeTypeDao;

    @Override
    public int updateById(Integer id, Integer code) {
        return changeCodeDao.updateById(id, code);
    }

    @Override
    public List<ChangeCode> selectByChangeCodeId(Integer codeType, Integer code) {
        return changeCodeDao.selectByChangeCodeId(codeType, code);
    }

    @Override
    public ChangeCode selectById(Integer keyId) {
        return changeCodeDao.selectById(keyId);
    }

    @Override
    public List<ChangeCode> selectByCodeTypeId(Integer codeType, Integer code) {
        return changeCodeDao.selectByCodeTypeId(codeType, code);
    }

    @Override
    public Integer up(String codeTypeId, MultipartFile file) {
        BizAssert.notNull(codeTypeId, BizCode.PARAM_NULL.getMessage());
        List<ExcelDataVO> excelDataVOS = ExcelUtil.readExcel(file);
        List<ChangeCode> changeCodes = new ArrayList<>();
        for (ExcelDataVO excelDataVO : excelDataVOS) {
            ChangeCode changeCode = new ChangeCode();
            changeCode.setKey(excelDataVO.getId() + "");
            changeCode.setCodeType(Integer.valueOf(Integer.parseInt(codeTypeId)));
            changeCode.setPassword(excelDataVO.getBarcode());
            changeCodes.add(changeCode);
        }
        int row = this.changeCodeDao.inserts(changeCodes);
        if (row == 0) {
            throw new BizException("");
        }
        int s = this.codeTypeDao.updateById(row, codeTypeId);
        if (s == 0) {
            throw new BizException("");
        }
        return row;
    }
}
