package com.mem.vo.business.base.dao;

import com.mem.vo.business.base.model.po.UserSignClass;
import org.springframework.data.repository.query.Param;

public interface SignInUserDao {
    Integer getUserAllIntegral(@Param("id") Long id);

    UserSignClass getsignShow(@Param("id") Long id);

    int addUserSign(UserSignClass userSignClass);

    int updateUserSign(UserSignClass userSignClass);

    Integer getUserTodayIntegral(@Param("userid") Long userid);
}
