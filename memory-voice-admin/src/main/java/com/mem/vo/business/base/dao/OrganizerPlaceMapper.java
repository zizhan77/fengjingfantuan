package com.mem.vo.business.base.dao;

import com.mem.vo.business.base.model.po.BasicPlace;
import com.mem.vo.business.base.model.po.OrganizerPlace;
import com.mem.vo.business.base.model.po.OrganizerPlaceExample;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrganizerPlaceMapper {
    long countByExample(OrganizerPlaceExample example);

    int deleteByExample(OrganizerPlaceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OrganizerPlace record);

    int insertSelective(OrganizerPlace record);

    List<OrganizerPlace> selectByExample(OrganizerPlaceExample example);

    OrganizerPlace selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OrganizerPlace record, @Param("example") OrganizerPlaceExample example);

    int updateByExample(@Param("record") OrganizerPlace record, @Param("example") OrganizerPlaceExample example);

    int updateByPrimaryKeySelective(OrganizerPlace record);

    int updateByPrimaryKey(OrganizerPlace record);

    @Select("select  id, place_id, organizer_id, create_time, create_user, update_time, update_user, is_delete from organizer_place where place_id=#{placeId} and organizer_id=#{userId} and is_delete = 0")
    List<BasicPlace> select(Long userId, String placeId);
    @Insert("INSERT INTO organizer_place (place_id,organizer_id) VALUES (#{placeId},#{organizerId}) ")
    int insertQuery(OrganizerPlace organizerPlace);
}