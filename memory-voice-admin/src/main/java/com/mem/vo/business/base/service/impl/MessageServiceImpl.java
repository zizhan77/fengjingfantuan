package com.mem.vo.business.base.service.impl;

import com.mem.vo.business.base.dao.MessageMapper;
import com.mem.vo.business.base.model.po.Message;
import com.mem.vo.business.base.model.po.MessageQuery;
import com.mem.vo.business.base.model.vo.MessageVO;
import com.mem.vo.business.base.service.MessageService;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.constant.SourceType;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("messageService")
@Transactional
public class MessageServiceImpl implements MessageService {
    @Resource
    private TokenService tokenService;

    @Resource
    private MessageMapper messageMapper;
    @Override
    public Page<MessageVO> queryAll(String token, MessageQuery messageQuery) {
        Message message = new Message();
        if(tokenService.getContextByken(token).getSourceCode().equals(SourceType.ORGENIZER.getCode())){
            Long userId = tokenService.getContextByken(token).getUserId();
            message.setOrganizerId(userId.intValue());
        }
        if (messageQuery.getStatus() != null && !"".equals(messageQuery.getStatus())){
            message.setStatus(messageQuery.getStatus());
        }

        Page<MessageVO> page = new Page<>();
        page.setPage(messageQuery.getPage());
        List<MessageVO> content=messageMapper.selectByMessage(page,message);
        BizAssert.notEmpty(content,BizCode.BIZ_1102.getMessage());
        System.out.println(content);
        page.setData(content);
        return page;
    }

    @Override
    public MessageVO queryById(Message message) {
        BizAssert.notNull(message, BizCode.PARAM_NULL.getMessage());
        BizAssert.notNull(message.getId(), BizCode.PARAM_NULL.getMessage());
        MessageVO message1 = messageMapper.selectById(message);
        BizAssert.notNull(message1,BizCode.BIZ_1101.getMessage());
        if("0".equals(message1.getStatus())){
            int i=messageMapper.updateStatusById(message.getId().longValue());
            if(i==0){
                throw new BizException("消息状态修改失败");
            }
        }
        return message1;
    }

    @Override
    public Message add(String token,Message message) {
        BizAssert.notNull(message, BizCode.PARAM_NULL.getMessage());
        BizAssert.notNull(message.getOrganizerId(), BizCode.PARAM_NULL.getMessage());
        Long userId = tokenService.getContextByken(token).getUserId();
        message.setIsDelete(0);
        message.setStatus("0");
        message.setSponsorId(userId.intValue());

        List<MessageVO> messageVOS = messageMapper.selectByMessage(new Page(), message);
        if(!messageVOS.isEmpty()){
            throw  new BizException("不要重复点击");
        }

        int insert = messageMapper.insert(message);
        if(insert==0){
            throw new BizException("增加消息操作失败");
        }

        return message;
    }

    @Override
    public String setRead(String token) {
        Long userId = tokenService.getContextByken(token).getUserId();
        int i=messageMapper.updateByOrganizerId(userId);
        if(i==0){
            throw new BizException("消息全部已读操作失败");
        }
        return i+"";
    }


}
