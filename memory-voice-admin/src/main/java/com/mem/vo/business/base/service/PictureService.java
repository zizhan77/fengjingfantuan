package com.mem.vo.business.base.service;

import java.util.List;

import com.mem.vo.business.base.model.po.Picture;
import com.mem.vo.business.base.model.po.PictureQuery;
import com.mem.vo.common.dto.Page;

/**
 * <br>
 * <b>功能：</b>PictureService<br>
 */
public interface PictureService {


    /**
     * 添加接单中台表
     *
     * @param pictureQuery 回复接单中台表实体
     * @return 返回添加的接单中台表的ID
     */
    int insert(Picture pictureQuery);

    /**
     * 更新接单中台表
     *
     * @param picture 更新接单中台表实体
     * @return 返回更新的接单中台表的ID
     */
    int updateById(Picture picture);

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
    Picture findById(Long id);

    /**
     * 根据条件查询接单中台表
     *
     * @param query 查询接单中台表条件
     * @return 返回查询的集合
     */
    List<Picture> findByCondition(PictureQuery query);

    /**
     * 根据条件查询接单中台表
     *
     * @param page 分页信息
     * @param query 查询条件
     */
    Page<Picture> findPageByCondition(Page page, PictureQuery query);
}
