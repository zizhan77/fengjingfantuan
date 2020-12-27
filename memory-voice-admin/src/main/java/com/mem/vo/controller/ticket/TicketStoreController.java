package com.mem.vo.controller.ticket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.mem.vo.business.base.model.po.*;
import com.mem.vo.business.base.service.PerformanceService;
import com.mem.vo.business.base.service.PerformanceShowService;
import com.mem.vo.business.base.service.TicketGearService;
import com.mem.vo.business.base.service.TicketStoreService;
import com.mem.vo.business.biz.model.vo.performance.TicketStoreRequest;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * <br>
 * <b>功能：</b>TicketStoreController<br>
 * <br>
 */
@RestController
@RequestMapping("/ticketStore")
@Slf4j
public class TicketStoreController {

    @Resource
    private TicketStoreService ticketStoreService;

    @Resource
    private TicketGearService ticketGearService;

    @Resource
    private PerformanceShowService performanceShowService;

    @Resource
    private PerformanceService performanceService;

    @RequestMapping("/createStoreReleaseWorker")
    public ResponseDto<String> createStoreReleaseWorker() {
        ResponseDto<String> responseDto = ResponseDto.successDto();
        try {
            return responseDto.successData(ticketStoreService.rollBackStock());
        } catch (Exception e) {

            log.error("创建库存回收worker失败，参数:{}", e);
            return responseDto.failSys();
        }
    }



    @PostMapping("/queryListById")
    public ResponseDto<JSONObject> queryListById(@RequestHeader("token") String token,TicketStoreRequest ticketStoreRequest) {
        ResponseDto<JSONObject> responseDto = ResponseDto.successDto();
        try {

            Performance performance= performanceService.findById(ticketStoreRequest.getPerformanceId());
            BizAssert.notNull(performance,"演出不存在");
            String showIDs = performance.getShowIds();
            BizAssert.hasText(showIDs,"场次为空");
            String[] showIds =  showIDs.split("~");
            List<String> shwoIdarr  = Arrays.asList(showIds);
            JSONObject jsonObject = new JSONObject();
            TicketStoreQuery ticketStoreQuery= new TicketStoreQuery();
            ticketStoreQuery.setShwoIdarr(shwoIdarr);
            ticketStoreQuery.setIsDelete(0);

            Page queryPage = new Page();
            queryPage.setPage(ticketStoreRequest.getPage());
            queryPage.setLimit(ticketStoreRequest.getLimit());
            Page<TicketStore> resPage = ticketStoreService.findPageByCondition(queryPage,ticketStoreQuery);
            if(resPage!=null){
                jsonObject.put("total",resPage.getTotal());
                if(CollectionUtils.isNotEmpty(resPage.getData())){
                    List<TicketStore> ticketStoreList = resPage.getData();
                    ticketStoreList.forEach(item->{
                        TicketGear ticketGear = ticketGearService.findById(item.getTicketGearId());
                        BizAssert.notNull(ticketGear,"找不到对应的票档");
                        item.setTicketGearName(ticketGear.getTicketGearName());

                        PerformanceShow performanceShow = performanceShowService.findById(item.getShowId());
                        BizAssert.notNull(performanceShow,"找不到对应的场次");
                        item.setShowName(performanceShow.getShowName());

                    });
                }
                jsonObject.put("list",resPage.getData());
            }
            return  responseDto.successData(jsonObject);
        } catch (Exception e) {

            log.error("查询库存列表异常，参数:{}", JsonUtil.toJson(ticketStoreRequest), e);
            return responseDto.failSys();
        }
    }


    @PostMapping("/updateTicketStore")
    @Transactional
    public ResponseDto<Boolean> updateTicketStore(@RequestHeader("token") String token,
        @RequestBody List<TicketStore> storageList) {
        ResponseDto<Boolean> responseDto = ResponseDto.successDto();
        try {
            BizAssert.notEmpty(storageList,"库存列表为空，参数错误");
            storageList.forEach(ticketStore->{
                BizAssert.notNull(ticketStore.getId(),"库存id为空，参数错误");
                ticketStoreService.updateById(ticketStore);
            });

            return responseDto.successData(true);
        } catch (Exception e) {

            log.error("修改库存失败,参数：{}", JsonUtil.toJson(storageList), e);
            return responseDto.failSys();
        }
    }


    public static void main(String[] args) {
        List<TicketStore> storageList = new ArrayList<>();
        TicketStore ticketStore = new TicketStore();
        ticketStore.setId(1L);
        ticketStore.setStoreNum(200);
        storageList.add(ticketStore);


        TicketStore ticketStore2 = new TicketStore();
        ticketStore2.setId(2L);
        ticketStore2.setStoreNum(300);
        storageList.add(ticketStore2);

        System.out.println(JsonUtil.toJson(storageList));


    }

}
