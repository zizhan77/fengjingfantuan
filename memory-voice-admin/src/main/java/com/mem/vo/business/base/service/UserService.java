package com.mem.vo.business.base.service;

import com.mem.vo.business.base.model.po.User;
import com.mem.vo.business.base.model.po.UserQuery;
import com.mem.vo.common.dto.Page;

import java.util.List;

/**
 * <br>
 * <b>功能：</b>UserService<br>
 */
public interface UserService {


    /**
     * 添加接单中台表
     *
     * @param userQuery 回复接单中台表实体
     * @return 返回添加的接单中台表的ID
     */
    int insert(User userQuery);

    /**
     * 更新接单中台表
     *
     * @param user 更新接单中台表实体
     * @return 返回更新的接单中台表的ID
     */
    int updateById(User user);

    /**
     * 删除接单中台表
     *
     * @param id 接单中台表ID
     */
    int deleteById(Long id);

    /**
     * 根据ID查询接单中台表
     *
     * @param id 接单中台表ID
     * @return 返回一条接单中台表
     */
    User findById(Long id);

    /**
     * 根据条件查询接单中台表
     *
     * @param query 查询接单中台表条件
     * @return 返回查询的集合
     */
    List<User> findByCondition(UserQuery query);

    /**
     * 根据条件查询接单中台表
     *
     * @param page 分页信息
     * @param query 查询条件
     */
    Page<User> findPageByCondition(Page page, UserQuery query);

    int updateBySourceAndBizCode(String phoneNumber,String sourceCode,String bizCode,String userName);
}
