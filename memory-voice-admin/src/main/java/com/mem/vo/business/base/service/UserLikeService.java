package com.mem.vo.business.base.service;


import com.mem.vo.business.base.model.po.UserLike;
import com.mem.vo.business.base.model.po.UserLikeQuery;
import com.mem.vo.business.base.model.vo.UserLikeVO;
import com.mem.vo.common.dto.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
/**
*
* <br>
* <b>功能：</b>UserLikeService<br>
*/
public interface UserLikeService {


    /**
    * 添加用户关注表
    * @param  userLikeQuery 回复用户关注表实体
    * @return 返回添加的用户关注表的ID
    */
    int insert(UserLike userLikeQuery);

    /**
    * 更新用户关注表
    * @param  userLike 更新用户关注表实体
    * @return 返回更新的用户关注表的ID
    */
    int updateById(UserLike userLike);

    /**
    * 删除用户关注表
    * @param  query 用户关注表ID
    */
    int deleteById(UserLikeQuery query);

    /**
    * 根据ID查询用户关注表
    * @param  id 用户关注表ID
    * @return 返回一条用户关注表
    */
    UserLike findById(Long id);

    /**
    * 根据条件查询用户关注表
    * @param  query 查询用户关注表条件
    * @return 返回查询的集合
    */
    List<UserLike> findByCondition(UserLikeQuery query);

    /**
    * 根据条件查询用户关注表
    * @param  page 分页信息
    * @param  query 查询条件
    */
    void findPageByCondition(Page page, UserLikeQuery query);

    List<UserLikeVO> selectAll(UserLikeQuery query);
}
