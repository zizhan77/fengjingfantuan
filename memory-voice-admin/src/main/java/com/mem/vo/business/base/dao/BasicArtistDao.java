package com.mem.vo.business.base.dao;

import com.mem.vo.business.base.model.po.BasicArtist;
import com.mem.vo.business.base.model.po.BasicArtistQuery;
import com.mem.vo.business.base.model.vo.PlaceArtistVO;
import com.mem.vo.common.dto.Page;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <br>
 * <b>功能：</b>BasicArtistDao<br>
 */
public interface BasicArtistDao {

    /**
     * 添加接单中台表实体
     *
     * @param basicArtist 回复接单中台表实体
     * @return 返回添加的接单中台表的ID
     */
    int insert(BasicArtist basicArtist);

    /**
     * 更新接单中台表
     *
     * @param basicArtist 更新接单中台表实体
     * @return 返回更新的接单中台表的ID
     */
    int updateById(BasicArtist basicArtist);

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
    BasicArtist findById(Long id);


    /**
     * 根据条件查询接单中台表
     *
     * @param query 查询条件
     * @return 返回查询的集合
     */
    List<BasicArtist> findByCondition(@Param("condition") BasicArtistQuery query);

    List<BasicArtist> findByName(@Param("page") Page page, @Param("name") String name);


    /**
     * 根据条件查询接单中台表
     *
     * @param page 分页信息
     * @param query 查询条件
     */
    List<BasicArtist> findByCondition(@Param("page") Page page, @Param("condition") BasicArtistQuery query);

    List<PlaceArtistVO> findByIdList(@Param("artistList") List<String> paramList);

    @Select({"select count(1) from basic_artist where is_delete = 0 and artist_name=#{name}"})
    int findByArtistName(@Param("name") String name);

}
