package com.mem.vo.controller.activity;
import com.mem.vo.business.base.model.po.Activity;
import com.mem.vo.business.base.model.po.ActivityUrl;
import com.mem.vo.business.base.service.ActivityUrlService;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.constant.SourceType;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.FileUtil;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/activityQaUrl"})
public class ActivityQaUrlController {
    private static final Logger log = LoggerFactory.getLogger(ActivityQaUrlController.class);

    @Resource
    private FileUtil fileUtil;

    @Resource
    private TokenService tokenService;

    @Resource
    private ActivityUrlService activityUrlService;

    @PostMapping({"/query"})
    public ResponseDto<List<ActivityUrl>> query(@RequestHeader("token") String token, ActivityUrl activityUrl) {
        ResponseDto<List<ActivityUrl>> responseDto = ResponseDto.successDto();
        try {
            return responseDto.successData(activityUrlService.query(activityUrl));
        } catch (BizException e) {
            log.error("查询活动拼图异常, 原因:{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("查询活动拼图异常", e);
            return responseDto.failSys();
        }
    }

    @PostMapping({"/queryPage"})
    public ResponseDto<Page<ActivityUrl>> queryPage(@RequestHeader("token") String token, ActivityUrl activityUrl, Page page) {
        ResponseDto<Page<ActivityUrl>> responseDto = ResponseDto.successDto();
        try {
            CommonLoginContext contextByken = tokenService.getContextByken(token);
            if (contextByken.getSourceCode().equals(SourceType.SPONSOR.getCode())) {
                Long userId = contextByken.getUserId();
                activityUrl.setSponsorId(userId.intValue() + "");
            }
            List<ActivityUrl> activityUrls = activityUrlService.queryPage(activityUrl, page);
            page.setData(activityUrls);
            return responseDto.successData(page);
        } catch (BizException e) {
            log.error("查询活动拼图异常, 原因:{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("查询活动拼图异常", e);
            return responseDto.failSys();
        }
    }

    @PostMapping({"/edit"})
    public ResponseDto<ActivityUrl> edit(@RequestHeader("token") String token, ActivityUrl activityUrl) {
        System.out.println(activityUrl);
        ResponseDto<ActivityUrl> responseDto = ResponseDto.successDto();
        CommonLoginContext contextByken = tokenService.getContextByken(token);
        activityUrl.setSponsorId(contextByken.getUserId() + "");
        try {
            return responseDto.successData(activityUrlService.edit(activityUrl));
        } catch (BizException e) {
            log.error("编辑活动拼图异常, 原因:{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("编辑活动拼图异常", e);
            return responseDto.failSys();
        }
    }

    @PostMapping({"/toBase64"})
    public ResponseDto<String> edit(String url) {
        ResponseDto<String> responseDto = ResponseDto.successDto();
        try {
            return responseDto.successData(fileUtil.image2Base64(url));
        } catch (BizException e) {
            log.error("编辑活动拼图异常, 原因:{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("编辑活动拼图异常", e);
            return responseDto.failSys();
        }
    }

    @PostMapping({"/queryOne"})
    public ResponseDto<ActivityUrl> queryOne(Activity a) {
        ResponseDto<ActivityUrl> responseDto = ResponseDto.successDto();
        try {
            return responseDto.successData(activityUrlService.queryOne(a.getId() + ""));
        } catch (BizException e) {
            log.error("活动拼图异常, 原因:{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("活动拼图异常", e);
            return responseDto.failSys();
        }
    }

    @PostMapping("/deleteById")
    public ResponseDto<Integer> deleteById(@RequestHeader("token") String token, String id) {
        //权限验证
        ResponseDto<Integer> responseDto = ResponseDto.successDto();

        try {
            BizAssert.notNull(id, BizCode.PARAM_NULL.getMessage());
            return responseDto.successData(activityUrlService.deleteById(Long.valueOf(id)));
        } catch (BizException e) {

            log.error("删除图片异常，参数:{},原因：{}", id, e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("删除图片异常，参数:{}", id, e);
            return responseDto.failSys();
        }
    }
}
