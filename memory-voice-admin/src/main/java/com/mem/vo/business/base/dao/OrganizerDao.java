package com.mem.vo.business.base.dao;


import com.mem.vo.business.base.model.po.Organizer;
import com.mem.vo.business.base.model.po.OrganizerExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrganizerDao {
    long countByExample(OrganizerExample example);

    int deleteByExample(OrganizerExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Organizer record);

    int insertSelective(Organizer record);

    List<Organizer> selectByExample(OrganizerExample example);

    Organizer selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Organizer record, @Param("example") OrganizerExample example);

    int updateByExample(@Param("record") Organizer record, @Param("example") OrganizerExample example);

    int updateByPrimaryKeySelective(Organizer record);

    int updateByPrimaryKey(Organizer record);

    @Select("select id, name, phone, password, status, create_time, create_user, update_time, update_user, is_delete from organizer where phone = #{phone} and is_delete = 0 ")
    List<Organizer> selectByUsername(@Param("phone") String username);

    @Select("select id, name, phone, password, status, create_time, create_user, update_time, update_user, is_delete from organizer where id = #{userId} and is_delete = 0 ")
    Organizer queryById(@Param("userId") Long userId);
}