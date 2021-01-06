package com.mem.vo.business.base.service;

import java.net.URISyntaxException;
import java.util.List;

import com.mem.vo.business.base.model.po.BasicPlace;
import com.mem.vo.business.base.model.po.BasicPlaceQuery;
import com.mem.vo.business.base.model.po.MtaBean;
import com.mem.vo.business.biz.model.vo.performance.BasicPlaceVo;
import com.mem.vo.common.dto.Page;
import org.apache.ibatis.annotations.Select;

/**
*
* <br>
* <b>功能：</b>BasicPlaceService<br>
*/
public interface BasicPlaceService {


    /**
    * 添加接单中台表
    * @param  basicPlaceVo 回复接单中台表实体
    * @return 返回添加的接单中台表的ID
    */
    int insert(BasicPlaceVo basicPlaceVo);

    /**
    * 更新接单中台表
    * @param basicPlaceVo 更新接单中台表实体
    * @return 返回更新的接单中台表的ID
    */
    int updateById(BasicPlaceVo basicPlaceVo);

    /**
    * 删除接单中台表
    * @param  id 接单中台表ID
    */
    int deleteById(Long id);

    /**
    * 根据ID查询接单中台表
    * @param  id 接单中台表ID
    * @return 返回一条接单中台表
    */
    BasicPlace findById(Long id);

    /**
    * 根据条件查询接单中台表
    * @param  query 查询接单中台表条件
    * @return 返回查询的集合
    */
    List<BasicPlace> findByCondition(BasicPlaceQuery query);

    /**
    * 根据条件查询接单中台表
    * @param  page 分页信息
    * @param  query 查询条件
    */
    Page<BasicPlace> findPageByCondition(Page page, BasicPlaceQuery query);


    List<String> findAllCity();

    List<BasicPlace> findByIdList(List<Long> list);

//    @Select("select * from basic_place where id=#{parseLong} and  is_delete= 0")
//    List<BasicPlace> findByPlaceId(long parseLong);

    MtaBean findHistory(String s,String e) throws URISyntaxException, Exception;
}
