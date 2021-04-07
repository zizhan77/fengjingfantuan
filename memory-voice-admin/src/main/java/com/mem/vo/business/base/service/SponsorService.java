package com.mem.vo.business.base.service;

import com.mem.vo.business.base.model.po.MtaBean;
import com.mem.vo.business.base.model.po.MtaData;
import com.mem.vo.business.base.model.po.Sponsor;
import com.mem.vo.business.base.model.po.SponsorQuery;
import com.mem.vo.common.dto.Page;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
/**
*
* <br>
* <b>功能：</b>SponsorService<br>
*/
public interface SponsorService {


    /**
    * 添加用户分享表
    * @param  sponsorQuery 回复用户分享表实体
    * @return 返回添加的用户分享表的ID
    */
    int insert(Sponsor sponsorQuery);

    /**
    * 更新用户分享表
    * @param  sponsor 更新用户分享表实体
    * @return 返回更新的用户分享表的ID
    */
    int updateById(Sponsor sponsor);

    /**
    * 删除用户分享表
    * @param  ids 用户分享表ID
    */
    int deleteById(List<Long> ids);

    /**
    * 根据ID查询用户分享表
    * @param  id 用户分享表ID
    * @return 返回一条用户分享表
    */
    Sponsor findById(Long id);

    /**
    * 根据条件查询用户分享表
    * @param  query 查询用户分享表条件
    * @return 返回查询的集合
    */
    List<Sponsor> findByCondition(SponsorQuery query);

    /**
    * 根据条件查询用户分享表
    * @param  page 分页信息
    * @param  query 查询条件
    */
    void findPageByCondition(Page page, SponsorQuery query);

    MtaData findHistory() throws URISyntaxException, IOException;

    MtaBean findArea(String start_time, String end_time) throws URISyntaxException, IOException;

    List<Sponsor> queryAllSponorByPhone(Long spid, Long activityid);

    List<String> querySponorPictureByactid(Long activityid);

    Sponsor getSponsorOne(Long id);
}
