package com.mem.vo.business.base.service;

import com.mem.vo.business.base.model.po.UserSignClass;

public interface SignInUserService {
    UserSignClass signIntegral(String token);

    UserSignClass signShow(String token);

    Integer signAddIntegral(String token);

    Integer signAddIntegralTest(Long userid);
}
