package com.mem.vo.business.base.dao;

import com.mem.vo.business.base.model.po.AgePo;
import com.mem.vo.business.base.model.po.MtaData;
import com.mem.vo.business.base.model.po.Ranking;
import com.mem.vo.business.base.model.po.User;
import com.mem.vo.business.base.model.po.UserQuery;
import com.mem.vo.business.base.model.vo.UserUpdatelottery;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.PageBean;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <br>
 * <b>功能：</b>UserDao<br>
 */
public interface UserDao {

    /**
     * 添加接单中台表实体
     *
     * @param user 回复接单中台表实体
     * @return 返回添加的接单中台表的ID
     */
    int insert(User user);

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
     * @param query 查询条件
     * @return 返回查询的集合
     */
    List<User> findByCondition(@Param("condition") UserQuery query);

    /**
     * 根据条件查询接单中台表
     *
     * @param page 分页信息
     * @param query 查询条件
     */
    List<User> findByCondition(@Param("page") Page page, @Param("condition") UserQuery query);

    int updateBySourceAndBizCode(@Param("phoneNumber")String phoneNumber,@Param("sourceCode")String sourceCode,@Param("bizCode")String bizCode,@Param("userName") String userName,@Param("gender") Integer gender, @Param("avatarurl") String avatarurl);


    @Select({"SELECT CASE gender  WHEN 1 THEN '  WHEN 2 THEN '  WHEN 0 THEN ' END AS gender,COUNT(1) AS COUNT FROM USER  WHERE`source_name` = 'AND `is_delete` = 0  GROUP BY `gender` "})
    List<MtaData> findGender();

    @Select({"SELECT COUNT(1) FROM USER WHERE YEAR(CURDATE())-YEAR(`birthday`)  >= #{startTime}  AND YEAR(CURDATE())-YEAR(`birthday`)<= #{endTime}"})
    String findAge(AgePo paramAgePo);

    @Update({"UPDATE user set integral = integral + #{code} where biz_code = #{userId}"})
    void addIntegral(@Param("code") Integer code, @Param("userId") String userId);

    @Update({"update user set integral= integral-#{integral} where biz_code = #{bizCode}"})
    int updateIntegral(@Param("bizCode") String bizCode, @Param("integral") Integer integral);

    void updateByIntegral(@Param("num") Integer num, @Param("userid") Long userid);

    Integer getUserWhoCount(@Param("id") Long paramLong);

    Integer getUserActCount(@Param("code") String paramString);

    Integer insertIntegralPageage(@Param("count") Integer count, @Param("flag") int flag, @Param("id") Long id);

    int myRankingByUserCount(@Param("id") Long paramLong);

    List<Ranking> myRankingByUser(@Param("paging") PageBean<Ranking> paramPageBean, @Param("id") Long paramLong);

    Integer getAdddessFlag(Long paramLong);

    List<UserUpdatelottery> userUpdatelottery(UserUpdatelottery paramUserUpdatelottery);

    Integer getlotteryqtyByuserCode(UserUpdatelottery paramUserUpdatelottery);

    Integer selectByCodeAndActid(UserUpdatelottery paramUserUpdatelottery);

    void updateByUserCodeForlottery(UserUpdatelottery paramUserUpdatelottery);

    void insertByCodeAndActid(UserUpdatelottery paramUserUpdatelottery);
}
