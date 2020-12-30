package com.mem.vo.business.base.dao;

import com.mem.vo.business.base.model.po.UserSignClass;
import org.springframework.data.repository.query.Param;

public interface SignInUserDao {
    Integer getUserAllIntegral(@Param("id") Long paramLong);

    UserSignClass getsignShow(@Param("id") Long paramLong);

    int addUserSign(UserSignClass paramUserSignClass);

    int updateUserSign(UserSignClass paramUserSignClass);

    Integer getUserTodayIntegral(@Param("userid") Long paramLong);
}
