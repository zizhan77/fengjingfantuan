package com.mem.vo.controller.activity;

import com.mem.vo.business.base.model.po.Activity;
import com.mem.vo.business.base.model.po.ActivityQuery;
import com.mem.vo.business.base.model.vo.DiceVO;
import com.mem.vo.business.base.service.ActivityService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.constant.Dice;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.config.annotations.CommonExHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 活动信息查询
 *
 * @author lvxiao
 * @version V1.0
 * @date 2019-06-16 15:15
 */
@RestController
@RequestMapping("/activity")
@Slf4j
public class ActivityController {

    @Resource
    private ActivityService activityService;

    @PostMapping("/queryAll")
    public ResponseDto<List<Activity>> queryAll(@RequestHeader("token") String token) {
        //权限验证
        ResponseDto<List<Activity>> responseDto = ResponseDto.successDto();


        try {
            return responseDto.successData(activityService.findByConditionAvailable(new ActivityQuery()));
        } catch (BizException e) {

            log.error("查询活动异常，原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("查询活动异常", e);
            return responseDto.failSys();
        }
    }

    @PostMapping("/queryAvailableList")
    public ResponseDto<List<Activity>> queryAllActivity(@RequestHeader("token") String token, ActivityQuery query) {
        //权限验证
        ResponseDto<List<Activity>> responseDto = ResponseDto.successDto();
        try {
            //不校验是否为空，返回所有
            return responseDto.successData(activityService.findByConditionAvailable(query));
        } catch (BizException e) {

            log.error("activity.queryAvailableList异常，原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("activity.queryAvailableList异常", e);
            return responseDto.failSys();
        }

    }

    @PostMapping("/add")
    public ResponseDto<Integer> addActivity(@RequestHeader("token") String token, Activity activity) {

        //权限验证
        ResponseDto<Integer> responseDto = ResponseDto.successDto();

        try {
            BizAssert.notNull(activity, BizCode.PARAM_NULL.getMessage());
            return responseDto.successData(activityService.insert(activity));
        } catch (BizException e) {

            log.error("创建活动异常，参数:{},原因：{}", activity.getId(), e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("创建活动异常，参数:{}", activity.getId(), e);
            return responseDto.failSys();
        }
    }

    @PostMapping("/updateById")
    public ResponseDto<Integer> updateById(@RequestHeader("token") String token, Activity activity) {

        //权限验证
        ResponseDto<Integer> responseDto = ResponseDto.successDto();

        try {
            BizAssert.notNull(activity.getId(), BizCode.PARAM_NULL.getMessage());
            return responseDto.successData(activityService.updateById(activity));
        } catch (BizException e) {

            log.error("更新活动异常，参数:{},原因：{}", activity.getId(), e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("更新活动异常，参数:{}", activity.getId(), e);
            return responseDto.failSys();
        }
    }

    @PostMapping("/deleteById")
    public ResponseDto<Integer> deleteById(@RequestHeader("token") String token, String id) {
        //权限验证
        ResponseDto<Integer> responseDto = ResponseDto.successDto();

        try {
            BizAssert.notNull(id, BizCode.PARAM_NULL.getMessage());
            return responseDto.successData(activityService.deleteById(Long.valueOf(id)));
        } catch (BizException e) {

            log.error("删除活动异常，参数:{},原因：{}", id, e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("删除活动异常，参数:{}", id, e);
            return responseDto.failSys();
        }
    }

    /**
     * 骰子接口，根据骰子的点数返回对应活动，因为活动只有三个写死，所以目前返回的活动就是固定的
     *
     * @param token 用户token
     * @return 返回活动信息
     */
    @CommonExHandler(key = "返回筛子对应活动")
    @PostMapping("/queryByPoints")
    public ResponseDto<DiceVO> queryByPoints(@RequestHeader("token") String token) {
        //权限验证
        ResponseDto<DiceVO> responseDto = ResponseDto.successDto();
        int points = (int) (1 + Math.random() * 6);
        DiceVO diceVO = DiceVO.builder().id(points).name(Dice.diceMap.get(points)).build();
        return responseDto.successData(diceVO);
    }

}
