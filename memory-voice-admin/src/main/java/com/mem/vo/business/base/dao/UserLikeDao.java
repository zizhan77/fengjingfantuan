package com.mem.vo.business.base.dao;


import java.util.Date;
import java.util.List;

import com.mem.vo.business.base.model.po.UserLike;
import com.mem.vo.business.base.model.po.UserLikeQuery;
import com.mem.vo.business.base.model.vo.UserLikeVO;
import com.mem.vo.common.dto.Page;
import org.apache.ibatis.annotations.Param;
/**
*
* <br>
* <b>功能：</b>UserLikeDao<br>
*/
public interface UserLikeDao {

    /**
    * 添加用户关注表实体
    * @param  userLike 回复用户关注表实体
    * @return 返回添加的用户关注表的ID
    */
    int insert(UserLike userLike);

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
    int deleteById(@Param("condition") UserLikeQuery query);

    /**
    * 根据ID查询用户关注表
    * @param  id 用户关注表ID
    * @return 返回一条用户关注表
    */
    UserLike findById(Long id);


    /**
    * 根据条件查询用户关注表
    * @param  query 查询条件
    * @return 返回查询的集合
    */
    List<UserLike> findByCondition(@Param("condition") UserLikeQuery query);

    /**
    * 根据条件查询用户关注表
    * @param  page 分页信息
    * @param  query 查询条件
    */
    void findByCondition(@Param("page") Page page, @Param("condition") UserLikeQuery query);

    List<UserLikeVO> selectAll(@Param("condition") UserLikeQuery query);
}
