package com.mem.vo.controller.login;

import cn.hutool.json.JSONObject;
import com.mem.vo.business.base.service.UserService;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.model.dto.DecodeWxUserInfoDto;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.constant.LoginStatus;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.AESUtil;
import com.mem.vo.common.util.JsonUtil;
import com.mem.vo.config.annotations.DontCheckLoginStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author litongwei
 * @description: 小程序绑定手机号接口
 * @date 2019/5/30 11:30
 */

@RestController
@RequestMapping("/minoPro")
@Slf4j
public class BindPhoneController {

    @Resource
    private TokenService tokenService;

    @Resource
    private UserService userService;


    @PostMapping("/bind/phone")
    @DontCheckLoginStatus
    public ResponseDto<Boolean> bindPhone(@RequestHeader String token,
        DecodeWxUserInfoDto decodeWxUserInfoDto) {

        log.info("绑定手机号接口入参：{}",JsonUtil.toJson(decodeWxUserInfoDto));
        ResponseDto<Boolean> responseDto = ResponseDto.successDto();
        try {
            CommonLoginContext context =  tokenService.getContextByken(token);
            BizAssert.hasText(decodeWxUserInfoDto.getEncryptedData(), "encryptedData" + BizCode.PARAM_NULL.getMessage());
            BizAssert.hasText(decodeWxUserInfoDto.getIv(), "iv" + BizCode.PARAM_NULL.getMessage());
            BizAssert.hasText(context.getSessionKey(), "session_key" + BizCode.PARAM_NULL.getMessage());
            CommonLoginContext commonLoginContext = tokenService.getContextByken(token);
            //解密数据
            JSONObject encodeObject = decodeUserInfo(decodeWxUserInfoDto.getEncryptedData(),
                context.getSessionKey(),
                decodeWxUserInfoDto.getIv());
            BizAssert.notNull(encodeObject.get("phoneNumber"), BizCode.BIZ_1011.getMessage());
            log.info("小程序解密后的数据：{}", JsonUtil.toJson(encodeObject));
            String phoneNumber = (String)encodeObject.get("phoneNumber");
            int gender = 0;
            String userName = "";
            String avatarUrl = "";
            System.out.println(decodeWxUserInfoDto.getUserInfo());
            if(StringUtils.isNotBlank(decodeWxUserInfoDto.getUserInfo())){
                //demo  {"nickName":"红鲤鱼与绿鲤鱼与驴","gender":1,"language":"zh_CN","city":"Chaoyang","province":"Beijing","country":"China","avatarUrl":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLxEySCn2IXXeMVX8AP8bAGdrusMMftlql9iaOsJzTytDVCGG7rzY4uzf0NIibpfdprvDJjAI8uXEnQ/132"}


                com.alibaba.fastjson.JSONObject jsonObject= com.alibaba.fastjson.JSONObject.parseObject(decodeWxUserInfoDto.getUserInfo());
                if(jsonObject.get("nickName")!=null){
                    userName = (String) jsonObject.get("nickName");
                }
                gender = ((Integer)jsonObject.get("gender")).intValue();
                if (jsonObject.get("avatarUrl") != null) {
                    avatarUrl = (String)jsonObject.get("avatarUrl");
                }
            }
            //手机号回写到数据库，修改数据库的授权状态
            userService.updateBySourceAndBizCode(phoneNumber,commonLoginContext.getSourceCode(),commonLoginContext.getBizCode(),userName,Integer.valueOf(gender), avatarUrl);
            commonLoginContext.setStatus(LoginStatus.SUCCESSFUL.getCode());
            //修改redis中的 token状态
            tokenService.updateTokenContext(token,commonLoginContext);

            return responseDto.successData(true);

        } catch (BizException e) {

            log.error("小程序绑定手机号异常，参数:{},原因：{}", JsonUtil.toJson(decodeWxUserInfoDto), e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("小程序绑定手机号系统异常，参数:{}", JsonUtil.toJson(decodeWxUserInfoDto), e);
            return responseDto.failSys();

        }

    }

    public JSONObject decodeUserInfo(String encrypted, String session_key, String iv) throws IOException {

        cn.hutool.json.JSONObject userencryptedData = AESUtil.wxDecrypt(encrypted, session_key, iv);

        BizAssert.isTrue(userencryptedData != null && userencryptedData.size() > 0, BizCode.BIZ_1010.getMessage());
        return userencryptedData;
    }


}
