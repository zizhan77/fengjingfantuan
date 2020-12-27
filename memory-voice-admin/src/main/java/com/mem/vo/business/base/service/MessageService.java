package com.mem.vo.business.base.service;

import com.mem.vo.business.base.model.po.Message;
import com.mem.vo.business.base.model.po.MessageQuery;
import com.mem.vo.business.base.model.vo.MessageVO;
import com.mem.vo.common.dto.Page;




public interface MessageService {
    Page<MessageVO> queryAll(String token, MessageQuery messageQuery);

    MessageVO queryById(Message message);


    Message add(String token,Message message);

    String setRead(String token);
}
