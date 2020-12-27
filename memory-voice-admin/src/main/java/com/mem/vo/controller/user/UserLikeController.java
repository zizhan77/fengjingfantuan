package com.mem.vo.controller.user;

import com.mem.vo.business.base.model.po.Performance;
import com.mem.vo.business.base.model.po.UserLike;
import com.mem.vo.business.base.model.po.UserLikeQuery;
import com.mem.vo.business.base.model.vo.UserLikeVO;
import com.mem.vo.business.base.service.PerformanceService;
import com.mem.vo.business.base.service.UserLikeService;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.config.annotations.CommonExHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-07-02 23:29
 */
@RestController
@RequestMapping("/user/like")
@Slf4j
public class UserLikeController {

    @Resource
    private UserLikeService userLikeService;
    @Resource
    private TokenService tokenService;

    @CommonExHandler(key = "增加关注列表")
    @PostMapping("/insert")
    public ResponseDto<Integer> insert(@RequestHeader String token, UserLike userLike) {
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        CommonLoginContext commonLoginContext = tokenService.getContextByken(token);
        BizAssert.notNull(userLike, BizCode.PARAM_NULL.getMessage());
        UserLikeQuery query = new UserLikeQuery();
        query.setUserId(Math.toIntExact(commonLoginContext.getUserId()));
        query.setPerformanceId(userLike.getPerformanceId());
        if (CollectionUtils.isNotEmpty(userLikeService.findByCondition(query))) {
            throw new BizException("您已关注该演出，不能重复关注哦~");
        }
        userLike.setUserId(Math.toIntExact(commonLoginContext.getUserId()));
        return responseDto.successData(userLikeService.insert(userLike));
    }

    @CommonExHandler(key = "查询关注列表")
    @PostMapping("/queryList")
    public ResponseDto<List<UserLikeVO>> queryListByToken(@RequestHeader String token, UserLikeQuery query) {
        ResponseDto<List<UserLikeVO>> responseDto = ResponseDto.successDto();
        CommonLoginContext commonLoginContext = tokenService.getContextByken(token);
        query.setUserId(Math.toIntExact(commonLoginContext.getUserId()));
        return responseDto.successData(userLikeService.selectAll(query));
    }


    @CommonExHandler(key = "更新关注列表")
    @PostMapping("/update")
    public ResponseDto<Integer> update(@RequestHeader String token, UserLike userLike) {
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        CommonLoginContext commonLoginContext = tokenService.getContextByken(token);
        BizAssert.notNull(userLike.getId(), BizCode.PARAM_NULL.getMessage());
        userLike.setUserId(Math.toIntExact(commonLoginContext.getUserId()));
        return responseDto.successData(userLikeService.updateById(userLike));
    }

    @CommonExHandler(key = "删除关注列表")
    @PostMapping("/delete")
    public ResponseDto<Integer> delete(@RequestHeader String token, UserLikeQuery query) {
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        BizAssert.notNull(query, BizCode.PARAM_NULL.getMessage());
        CommonLoginContext commonLoginContext = tokenService.getContextByken(token);
        query.setUserId(Math.toIntExact(commonLoginContext.getUserId()));
        return responseDto.successData(userLikeService.deleteById(query));
    }

    @CommonExHandler(key = "是否关注")
    @PostMapping("/isLike")
    public ResponseDto<Integer> isLike(@RequestHeader String token, UserLikeQuery query) {
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        BizAssert.notNull(query.getPerformanceId(), BizCode.PARAM_NULL.getMessage());
        CommonLoginContext commonLoginContext = tokenService.getContextByken(token);
        query.setUserId(Math.toIntExact(commonLoginContext.getUserId()));
        List<UserLike> likeList = userLikeService.findByCondition(query);
        return responseDto.successData(CollectionUtils.isEmpty(likeList) ? 0 : 1);
    }
}
