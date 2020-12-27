package com.mem.vo.business.base.service;

import com.mem.vo.common.dto.ResponseDto;

public interface OrganizerPlaceService {
    ResponseDto<Boolean> insert(String token, String placeId);
}
