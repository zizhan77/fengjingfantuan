package com.mem.vo.controller.organizer.message;

import com.mem.vo.business.base.model.po.Message;
import com.mem.vo.business.base.model.po.MessageQuery;
import com.mem.vo.business.base.model.vo.MessageVO;
import com.mem.vo.business.base.service.MessageService;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/organizer/message")
public class MessageController {

    @Resource
    private MessageService messageService;

    @PostMapping("/queryAll")
    public ResponseDto<Page<MessageVO>> queryAll(@RequestHeader("token") String token, MessageQuery messageQuery) {
        ResponseDto<Page<MessageVO>> responseDto = ResponseDto.successDto();
        System.out.println("传进来的status是："+messageQuery.getStatus());
        try {
            Page<MessageVO> list = messageService.queryAll(token, messageQuery);
            return responseDto.successData(list);

        } catch (BizException e) {
            log.error("查询消息业务异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("查询消息异常", e);
            return responseDto.failSys();
        }
    }

    @PostMapping("/queryById")
    public ResponseDto<MessageVO> queryById(Message message) {
        ResponseDto<MessageVO> responseDto = ResponseDto.successDto();
        try {
            MessageVO list = messageService.queryById(message);
            return responseDto.successData(list);
        } catch (BizException e) {
            log.error("查询消息业务异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("查询消息异常", e);
            return responseDto.failSys();
        }
    }


    @PostMapping("/add")
    public ResponseDto<Message> add(@RequestHeader("token") String token, Message message) {
        ResponseDto<Message> responseDto = ResponseDto.successDto();
        try {
            Message list = messageService.add(token,message);
            return responseDto.successData(list);
        } catch (BizException e) {
            log.error("添加消息业务异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("添加消息异常", e);
            return responseDto.failSys();
        }
    }



    @PostMapping("/setRead")
    public ResponseDto<String> setRead(@RequestHeader("token") String token) {
        ResponseDto<String> responseDto = ResponseDto.successDto();
        try {
            String list = messageService.setRead(token);
            return responseDto.successData(list);
        } catch (BizException e) {
            log.error("添加消息业务异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("添加消息异常", e);
            return responseDto.failSys();
        }
    }

}
