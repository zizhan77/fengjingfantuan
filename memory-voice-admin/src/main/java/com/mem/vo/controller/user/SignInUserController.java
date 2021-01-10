package com.mem.vo.controller.user;

import com.mem.vo.business.base.model.po.UserSignClass;
import com.mem.vo.business.base.service.SignInUserService;
import com.mem.vo.common.dto.ResponseDto;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/user/sign"})
public class SignInUserController {
    private static final Logger log = LoggerFactory.getLogger(SignInUserController.class);

    @Resource
    private SignInUserService signInUserService;

    @PostMapping({"/phone/signInAddIntegral"})
    public ResponseDto<Integer> signAddIntegral(@RequestHeader("token") String token) {
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        Integer userSignClass = signInUserService.signAddIntegral(token);
        responseDto.setData(userSignClass);
        return responseDto;
    }

    @GetMapping({"/phone/signInAddIntegral/test"})
    public ResponseDto<Integer> signAddIntegraltest(Long userid) {
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        Integer userSignClass = signInUserService.signAddIntegralTest(userid);
        responseDto.setData(userSignClass);
        return responseDto;
    }

    @PostMapping({"/phone/signIntegral"})
    public ResponseDto<UserSignClass> signIntegral(@RequestHeader("token") String token) {
        ResponseDto<UserSignClass> responseDto = ResponseDto.successDto();
        UserSignClass userSignClass = signInUserService.signIntegral(token);
        responseDto.setData(userSignClass);
        return responseDto;
    }

    @PostMapping({"/phone/signShow"})
    public ResponseDto<UserSignClass> signShow(@RequestHeader("token") String token) {
        ResponseDto<UserSignClass> responseDto = ResponseDto.successDto();
        UserSignClass userSignClass = signInUserService.signShow(token);
        responseDto.setData(userSignClass);
        return responseDto;
    }
}
