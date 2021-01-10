package com.mem.vo.controller.smallprogramCode;

import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.util.HttpXmlClient;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/smallprogramcode"})
public class SmallprogramCodeController {
    private static final Logger log = LoggerFactory.getLogger(SmallprogramCodeController.class);

    @Value("${wx.appid}")
    private String appid;

    @Value("${wx.secret}")
    private String secret;

    @Resource
    private TokenService tokenService;

    @PostMapping(value = {"/getSmallProgramCode"}, produces = {"text/plain;charset=UTF-8"})
    public String getSmallCode(@RequestHeader("token") String token, @RequestParam String path, @RequestParam String param) {
        try {
            String requestStr = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + this.appid + "&secret=" + this.secret;
            String tok = "";
            String[] tokena = HttpXmlClient.sendGet(requestStr, "").split("\"");
            for (int i = 0; i <= tokena.length - 1; i++) {
                System.out.println("---" + tokena[i]);
                if (i == 3) {
                    tok = tokena[i];
                }
            }
            CommonLoginContext contextByken = this.tokenService.getContextByken(token);
            String str = param + "&u=" + contextByken.getUserId();
            String requeststr2 = "https://api.weixin.qq.com/wxa/getwxacode?access_token=" + tok;
            String parama = path + "?param=" + str;
            return HttpXmlClient.sendPost3(requeststr2, parama);
        } catch (Exception e) {
            log.debug("获取小程序码出问题", e.getMessage());
            return "0";
        }
    }
}
