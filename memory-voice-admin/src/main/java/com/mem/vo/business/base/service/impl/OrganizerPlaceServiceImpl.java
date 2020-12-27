package com.mem.vo.business.base.service.impl;

import com.mem.vo.business.base.dao.OrganizerPlaceMapper;
import com.mem.vo.business.base.model.po.BasicPlace;
import com.mem.vo.business.base.model.po.OrganizerPlace;
import com.mem.vo.business.base.service.BasicPlaceService;
import com.mem.vo.business.base.service.OrganizerPlaceService;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("organizerPlaceService")
@Transactional
public class OrganizerPlaceServiceImpl implements OrganizerPlaceService {
    @Resource
    private TokenService tokenService;

    @Resource
    private OrganizerPlaceMapper organizerPlaceMapper;

    @Resource
    private BasicPlaceService basicPlaceService;
    @Override
    public ResponseDto<Boolean> insert(String token, String placeId) {
        BizAssert.notNull(token, BizCode.PARAM_NULL.getMessage());
        BizAssert.notNull(placeId, BizCode.PARAM_NULL.getMessage());
        BasicPlace byId = basicPlaceService.findById(Long.parseLong(placeId));
        System.out.println(byId);
        BizAssert.notNull(byId,BizCode.BIZ_1013.getMessage());
        Long userId = tokenService.getContextByken(token).getUserId();
        OrganizerPlace organizerPlace = new OrganizerPlace();
        organizerPlace.setPlaceId(Long.parseLong(placeId));
        organizerPlace.setOrganizerId(userId);
        List list1=organizerPlaceMapper.select(userId,placeId);
        if(!list1.isEmpty()){
            throw new BizException("不要重复点击");
        }
        int insert = organizerPlaceMapper.insertQuery(organizerPlace);
        ResponseDto<Boolean> objectResponseDto = new ResponseDto<>();
        if(insert==0){
            throw new BizException("操作失败");
        }
        objectResponseDto.successData(true);
        return objectResponseDto;
    }
}
