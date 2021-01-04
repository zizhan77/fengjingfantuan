package com.mem.vo.business.base.service;

import com.mem.vo.business.base.model.po.UserSignClass;

public interface SignInUserService {
    UserSignClass signIntegral(String paramString);

    UserSignClass signShow(String paramString);

    Integer signAddIntegral(String paramString);

    Integer signAddIntegralTest(Long paramLong);
}
