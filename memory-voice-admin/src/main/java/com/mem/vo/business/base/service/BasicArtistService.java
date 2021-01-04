package com.mem.vo.business.base.service;

import java.util.List;

import com.mem.vo.business.base.model.po.BasicArtist;
import com.mem.vo.business.base.model.po.BasicArtistQuery;
import com.mem.vo.business.base.model.vo.PlaceArtistVO;
import com.mem.vo.common.dto.Page;

/**
 * <br>
 * <b>功能：</b>BasicArtistService<br>
 */
public interface BasicArtistService {


    /**
     * 添加接单中台表
     *
     * @param basicArtistQuery 回复接单中台表实体
     * @return 返回添加的接单中台表的ID
     */
    int insert(BasicArtist basicArtistQuery);

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
     * @param query 查询接单中台表条件
     * @return 返回查询的集合
     */
    List<BasicArtist> findByCondition(BasicArtistQuery query);

    Page<BasicArtist> findByName(Page page, String name);

    /**
     * 根据条件查询接单中台表
     *
     * @param page 分页信息
     * @param query 查询条件
     */
    Page<BasicArtist> findPageByCondition(Page page, BasicArtistQuery query);

    List<PlaceArtistVO> findByIdList(List<String> paramList);
}
