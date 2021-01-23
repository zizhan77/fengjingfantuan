package com.mem.vo.controller.prize;

import com.mem.vo.business.base.model.po.*;
import com.mem.vo.business.base.service.ChangeCodeService;
import com.mem.vo.business.base.service.CodeTypeService;
import com.mem.vo.business.base.service.PrizeService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.config.annotations.CommonExHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 奖品信息
 *
 * @author lvxiao
 * @version V1.0
 * @date 2019-06-16 14:52
 */
@RestController
@RequestMapping("/prize")
@Slf4j
public class PrizeController {

    @Resource
    private ChangeCodeService ChangeCodeService;

    @Resource
    private CodeTypeService codeTypeService;

    @Resource
    private PrizeService prizeService;

    @PostMapping({"/pc/addchangecodeall"})
    public ResponseDto<Integer> addchangecodeall(Long activityId) {
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        try {
            int a = prizeService.addchangecodeall(activityId);
            return responseDto.successData(Integer.valueOf(a));
        } catch (Exception e) {
            log.error("查询奖品异常", e);
            return responseDto.failSys();
        }
    }

    @PostMapping("/queryAll")
    public ResponseDto<List<Prize>> queryAllPrize(@RequestHeader("token") String token,PrizeQuery query) {

        //权限验证
        ResponseDto<List<Prize>> responseDto = ResponseDto.successDto();
        try {
            return responseDto.successData(prizeService.findByCondition(query));
        } catch (BizException e) {
            log.error("查询异常，原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("查询奖品异常", e);
            return responseDto.failSys();
        }
    }

    @CommonExHandler(key = "插入奖品")
    @PostMapping("/insert")
    public ResponseDto<Integer> inset(@RequestHeader("token") String token, Prize prize) {
        //权限验证
        ResponseDto<Integer> responseDto = ResponseDto.successDto();

            Long activityId = prize.getActivityId();
            BizAssert.notNull(activityId, BizCode.PARAM_NULL.getMessage());
            Integer prizeType = prize.getPrizeType();
            BizAssert.notNull(prizeType, BizCode.PARAM_NULL.getMessage());
            BizAssert.notNull(prize.getLevel(), BizCode.PARAM_NULL.getMessage());
            //是否存在该类奖品
//            PrizeQuery query = PrizeQuery.builder()
//                    .activityId(activityId)
//                    .prizeType(prizeType)
//                    .build();
            PrizeQuery query = PrizeQuery.builder()
                    .activityId(activityId)
                    .level(prize.getLevel()).build();
            List<Prize> prizeList = prizeService.findByCondition(query);
            if (CollectionUtils.isNotEmpty(prizeList)) {
                throw new BizException("已经存在相同类型的奖品！");
            }
            return responseDto.successData(prizeService.insert(prize));

    }

    @CommonExHandler(key = "批量插入奖品")
    @PostMapping("/insetByBatch")
    public ResponseDto<Integer> insetByBatch(@RequestHeader("token") String token, List<Prize> prizeList) {
        //权限验证
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        BizAssert.notNull(prizeList, BizCode.PARAM_NULL.getMessage());
        //批量插入奖品
        return null;
    }

    @PostMapping("/update")
    public ResponseDto<Integer> update(@RequestHeader("token") String token, Prize prize) {
        //权限验证
        ResponseDto<Integer> responseDto = ResponseDto.successDto();

        try {
            BizAssert.notNull(prize, BizCode.PARAM_NULL.getMessage());
            return responseDto.successData(prizeService.updateById(prize));
        } catch (BizException e) {

            log.error("更新奖品异常，原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("更新奖品异常", e);
            return responseDto.failSys();
        }
    }

    @CommonExHandler(key = "用户抽奖")
    @PostMapping("/lottery")
    public ResponseDto<PrizeD> lottery(@RequestHeader("token") String token, Long activityId) {
        //权限验证
        ResponseDto<PrizeD> responseDto = ResponseDto.successDto();
        PrizeD prizeD = prizeService.slotMachine(token, activityId);
        if (prizeD.getKeyId() != null && !"".equals(prizeD.getKeyId())) {
            ChangeCode changeCode = ChangeCodeService.selectById(prizeD.getKeyId());
            prizeD.setChangeCode(changeCode);
            CodeType codeType = codeTypeService.selectById(prizeD.getCodeType());
            prizeD.setCodeTypeBean(codeType);
        }
        return responseDto.successData(prizeService.slotMachine(token, activityId));
    }

    @PostMapping({"/deleteById"})
    public ResponseDto<Integer> deleteById(@RequestHeader("token") String token, Long id) {
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        log.error("===================================删除奖品");
        try {
            BizAssert.notNull(id, BizCode.PARAM_NULL.getMessage());
            return responseDto.successData(prizeService.deleteById(id));
        } catch (BizException e) {
            log.error("删除奖品异常，参数:{},原因:{}", id, e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("删除奖品异常,参数{}", id, e);
            return responseDto.failSys();
        }
    }
}
