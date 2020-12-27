package com.mem.vo.business.base.dao;

import com.mem.vo.business.base.model.po.Message;
import com.mem.vo.business.base.model.po.MessageExample;
import com.mem.vo.business.base.model.po.OrderQuery;
import com.mem.vo.business.base.model.vo.MessageVO;
import com.mem.vo.common.dto.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MessageMapper {
    long countByExample(MessageExample example);

    int deleteByExample(MessageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Message record);

    int insertSelective(Message record);

    List<Message> selectByExample(MessageExample example);

    Message selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Message record, @Param("example") MessageExample example);

    int updateByExample(@Param("record") Message record, @Param("example") MessageExample example);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);



    MessageVO selectById(Message message);

    List<MessageVO> selectByMessage(@Param("page") Page page, @Param("message") com.mem.vo.business.base.model.po.Message message);


    int updateStatusById(Long id);

    int updateByOrganizerId(Long organizerId);
}