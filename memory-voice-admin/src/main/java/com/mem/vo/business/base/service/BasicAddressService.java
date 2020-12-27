package com.mem.vo.business.base.service;

import com.mem.vo.business.base.model.po.BasicAddress;
import com.mem.vo.business.base.model.po.BasicAddressQuery;
import com.mem.vo.common.dto.Page;

import java.util.List;

/**
 * <br>
 * <b>功能：</b>BasicAddressService<br>
 */
public interface BasicAddressService {


    /**
     * 添加接单中台表
     *
     * @param basicAddressQuery 回复接单中台表实体
     * @return 返回添加的接单中台表的ID
     */
    int insert(BasicAddress basicAddressQuery);

    /**
     * 更新接单中台表
     *
     * @param basicAddress 更新接单中台表实体
     * @return 返回更新的接单中台表的ID
     */
    int updateById(BasicAddress basicAddress);

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
    BasicAddress findById(Long id);

    /**
     * 根据条件查询接单中台表
     *
     * @param query 查询接单中台表条件
     * @return 返回查询的集合
     */
    List<BasicAddress> findByCondition(BasicAddressQuery query);

    /**
     * 根据条件查询接单中台表
     *
     * @param page 分页信息
     * @param query 查询条件
     */
    Page<BasicAddress> findPageByCondition(Page page, BasicAddressQuery query);

    String findNameByCode(String code);

}
