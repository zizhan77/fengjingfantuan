package com.mem.vo.controller.seat;

import com.alibaba.fastjson.JSON;
import com.mem.vo.business.base.model.po.*;
import com.mem.vo.business.base.model.vo.ShowSeatsVO;
import com.mem.vo.business.base.service.*;
import com.mem.vo.business.biz.model.vo.exchangecode.ExchangeCodeRequest;
import com.mem.vo.business.biz.model.vo.performance.BasicFreightVo;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.constant.ExchangeCodeBizType;
import com.mem.vo.common.constant.SeatStatus;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.BeanCopyUtil;
import com.mem.vo.common.util.RedisUtils;
import com.mem.vo.config.annotations.CommonExHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author litongwei
 * @description: 演出
 * @date 2019/5/25 15:43
 */

@RestController
@RequestMapping("/seats")
@Slf4j
public class SeatsController {

    @Resource
    private SeatsService seatsService;

    @Resource
    private ShowSeatsService showSeatsService;

    @Resource
    private AreaService areaService;

    @Resource
    private TicketStoreService ticketStoreService;

    @Resource
    private TicketGearService ticketGearService;

    @Resource
    private ExchangeCodeMainService exchangeCodeMainService;
    @Resource
    private RedisUtils redisUtils;

    //查询座位状态
    @PostMapping("/querySeatListByShowId")
    public ResponseDto<List<ShowSeatsVO>> querySeatListByShowId(@RequestHeader("token") String token, ShowSeatsQuery showSeatsQuery) {

        //权限验证

        ResponseDto<List<ShowSeatsVO>> responseDto = ResponseDto.successDto();

        try {
            BizAssert.notNull(showSeatsQuery.getShowId(), BizCode.PARAM_NULL.getMessage());
            AreaQuery areaQuery = new AreaQuery();
            areaQuery.setPlaceId(showSeatsQuery.getPlaceId());
            List<Area> areaList= areaService.findByCondition(areaQuery);
            List<ShowSeats> showSeatsList = showSeatsService.findByCondition(showSeatsQuery);
            List<ShowSeatsVO> showSeatsVOS = new LinkedList<>();
            for (Area a:areaList){
                for (ShowSeats s:showSeatsList){
                    if (a.getId().equals(s.getAreaId())){
                        ShowSeatsVO showSeatsVO =new ShowSeatsVO();
                        showSeatsVO = BeanCopyUtil.copyProperties(s,showSeatsVO.getClass());
                        showSeatsVO.setAreaVo(a);
                        showSeatsVOS.add(showSeatsVO);
                    }
                }
            }
            int tempLine = 0;
            int tempSeatNo = 0;
            for (ShowSeatsVO s:showSeatsVOS){
                if(tempLine!=Integer.parseInt(s.getLineNo())){
                    tempSeatNo = 1;
                    tempLine = Integer.parseInt(s.getLineNo());
                }else if(s.getStatus()!=0){
                    tempSeatNo++;
                }else{
                    continue;
                }
                s.setSeatNo(tempSeatNo+"");
            }
            return responseDto.successData(showSeatsVOS);

        } catch (BizException e) {

            log.error("获取座位列表异常，参数:{},原因：{}", showSeatsQuery.getShowId(), e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("获取座位列表异常，参数:{}", showSeatsQuery.getShowId(), e);
            return responseDto.failSys();

        }
    }
    //查询座位状态
    @PostMapping("/querySeatListByPlaceId")
    public ResponseDto<List<ShowSeatsVO>> querySeatListByPlaceId(@RequestHeader("token") String token, SeatsQuery seatsQuery) {

        //权限验证

        ResponseDto<List<ShowSeatsVO>> responseDto = ResponseDto.successDto();

        try {
            BizAssert.notNull(seatsQuery.getPlaceId(), BizCode.PARAM_NULL.getMessage());
            AreaQuery areaQuery = new AreaQuery();
            areaQuery.setPlaceId(seatsQuery.getPlaceId());
            List<Area> areaList= areaService.findByCondition(areaQuery);
            List<Seats> seatsList = seatsService.findByCondition(seatsQuery);
            List<ShowSeatsVO> showSeatsVOS = new LinkedList<>();
            for (Area a:areaList){
                for (Seats s:seatsList){
                    if (a.getId().equals(s.getAreaId())){
                        ShowSeatsVO showSeatsVO =new ShowSeatsVO();
                        showSeatsVO = BeanCopyUtil.copyProperties(s,showSeatsVO.getClass());
                        showSeatsVO.setAreaVo(a);
                        showSeatsVOS.add(showSeatsVO);
                    }
                }
            }
            int tempLine = 0;
            int tempSeatNo = 0;
            for (ShowSeatsVO s:showSeatsVOS){
                if(tempLine!=Integer.parseInt(s.getLineNo())){
                    tempSeatNo = 1;
                    tempLine = Integer.parseInt(s.getLineNo());
                }else if(s.getStatus()!=0){
                    tempSeatNo++;
                }else{
                    continue;
                }
                s.setSeatNo(tempSeatNo+"");
            }
            return responseDto.successData(showSeatsVOS);

        } catch (BizException e) {

            log.error("获取座位列表异常，参数:{},原因：{}", seatsQuery.getPlaceId(), e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("获取座位列表异常，参数:{}", seatsQuery.getPlaceId(), e);
            return responseDto.failSys();

        }
    }
    //查询座位模板
    @PostMapping("/querySeatModel")
    public ResponseDto<List<Seats>> querySeatModel(@RequestHeader("token") String token, SeatsQuery seatsQuery) {
        //权限验证
        ResponseDto<List<Seats>> responseDto = ResponseDto.successDto();
        try {
            BizAssert.notNull(seatsQuery, BizCode.PARAM_NULL.getMessage());
            return responseDto.successData(seatsService.findByCondition(seatsQuery));
        } catch (BizException e) {
            log.error("获取座位模板列表异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("获取座位模板列表异常，参数:{}", e);
            return responseDto.failSys();

        }
    }
    //新增座位模板
    @PostMapping("/initSeatList")
    public ResponseDto<String> initSeatList(@RequestHeader("token") String token ,String seatsListJson) {
        ResponseDto<String> responseDto = ResponseDto.successDto();
        try {
            List<Seats> seatsList= JSON.parseArray(seatsListJson,Seats.class);
            if(seatsList.size()<=0){
                throw new BizException(BizCode.PARAM_NULL.getMessage());
            }
            SeatsQuery sq = new SeatsQuery();
            sq.setPlaceId(seatsList.get(0).getPlaceId());
            List<Seats> curSeatModelList = seatsService.findByCondition(sq);

            Set <String> lineNoSet = new HashSet<>();
            for (Seats seats:seatsList) {
                lineNoSet.add(seats.getLineNo());
            }
            for (Seats seats:curSeatModelList){
                if(lineNoSet.contains(seats.getLineNo())){
                    throw new BizException(BizCode.BIZ_1037.getMessage());
                }
            }
            for(Seats s:seatsList){
                if(seatsService.insert(s)<=0)
                    throw new BizException(BizCode.BIZ_1035.getMessage());
            }
            return responseDto.successData("新增成功");
        } catch (BizException e) {
            log.error("新增座位模板结果异常,原因：{}",  e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("新增座位模板结果异常",  e);
            return responseDto.failSys();
        }
    }


    //编辑座位模板接口
    @PostMapping("/editSeatList")
    public ResponseDto<String> editSeatList(@RequestHeader("token") String token ,ShowSeatsQuery sq) {
        ResponseDto<String> responseDto = ResponseDto.successDto();
        try {
            if(sq.getLineNo()== null){
                throw new BizException(BizCode.PARAM_NULL.getMessage());
            }
            if(sq.getAreaId()== null){
                throw new BizException(BizCode.PARAM_NULL.getMessage());
            }
            //查询座位状态，如果有售出，禁止编辑座位
            List<ShowSeats> tempList=showSeatsService.findByCondition(sq);
            sq.setStatus(SeatStatus.SELLOUT.getCode());
            if (tempList.size()>0){
                throw new BizException(BizCode.BIZ_1032.getMessage());
            }
            for(ShowSeats s:tempList){
                SeatsQuery seatsq = new SeatsQuery();
                seatsq = BeanCopyUtil.copyProperties(s,seatsq.getClass());
                List<Seats> sList=seatsService.findByCondition(seatsq);
                for(Seats ss:sList){
                    if(seatsService.updateById(ss)<=0)
                        throw new BizException(BizCode.BIZ_1032.getMessage());
                }
            }
            return responseDto.successData("编辑座位成功");
        } catch (BizException e) {
            log.error("编辑座位异常,原因：{}",  e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("编辑座位异常",  e);
            return responseDto.failSys();
        }
    }

    //批量编辑座位模板接口
    @Transactional
    @PostMapping("/editBatchSeatList")
    @CommonExHandler(key = "批量编辑座位模板")
        public ResponseDto<String> editBatchSeatList(@RequestHeader("token") String token ,String seatsJson) {
        ResponseDto<String> responseDto = ResponseDto.successDto();
        try {
            if(seatsJson == null){
                throw new BizException(BizCode.PARAM_NULL.getMessage());
            }
            //查询座位状态，如果有售出，禁止编辑座位
            List<Seats> poList= JSON.parseArray(seatsJson,Seats.class);
            if (poList.size()<= 0){
                throw new BizException(BizCode.PARAM_NULL.getMessage());
            }
            for(Seats ss:poList){
                seatsService.updateById(ss);
            }
            return responseDto.successData("批量编辑座位成功");
        } catch (BizException e) {
            log.error("批量编辑座位异常,原因：{}",  e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("批量编辑座位异常",  e);
            return responseDto.failSys();
        }
    }
    //批量生成兑换码座位模板接口
    @Transactional
    @PostMapping("/createBatchSeatExchangeCode")
    @CommonExHandler(key = "批量生成兑换码座位模板")
    public ResponseDto<String> createBatchSeatExchangeCode(@RequestHeader("token") String token ,String seatsJson) {
        ResponseDto<String> responseDto = ResponseDto.successDto();
        try {
            if(seatsJson == null){
                throw new BizException(BizCode.PARAM_NULL.getMessage());
            }
            //查询座位状态，如果有售出，禁止编辑座位
            List<ShowSeatsVO> showSeatsVOS= JSON.parseArray(seatsJson,ShowSeatsVO.class);
            if (showSeatsVOS.size() <= 0){
                throw new BizException(BizCode.PARAM_NULL.getMessage());
            }
            List<ShowSeats> poList = showSeatsVOS.stream().map(item ->
            {
                return BeanCopyUtil.copyProperties(item, ShowSeats.class);
            }).collect(Collectors.toList());
            ShowSeatsQuery showSeatsQuery = new ShowSeatsQuery();
            showSeatsQuery.setShowId(showSeatsVOS.get(0).getShowId());
            showSeatsQuery.setStatus(SeatStatus.SELLOUT.getCode());
            List<ShowSeats> tempList=showSeatsService.findByCondition(showSeatsQuery);
            if (tempList.size()>0){
                throw new BizException(BizCode.BIZ_1032.getMessage());
            }
            TicketStoreQuery ticketStoreQuery = new TicketStoreQuery();
            ticketStoreQuery.setShowId(poList.get(0).getShowId());
            List<TicketStore> ticketStoreList = ticketStoreService.findByCondition(ticketStoreQuery);
            TicketGearQuery ticketGearQuery = new TicketGearQuery();
            List<TicketGear> ticketGearList = ticketGearService.findByCondition(ticketGearQuery);
            Map<String,String> atRelationMap = new HashMap<>();
            for(TicketGear t:ticketGearList){
                atRelationMap.put(t.getAreaId()+"",t.getId()+"");
            }
            for(ShowSeats ss:poList){
                ShowSeats curss= showSeatsService.findById(ss.getShowId());
                if(!curss.getStatus().equals(ss.getStatus())){
                    int storeChange= ss.getStatus() == 3 ? -1 : 0;
                    String seatsGearId = atRelationMap.get(ss.getAreaId()+"");
                    for (TicketStore ticketStore:ticketStoreList){
                        ticketStore.setShowId(ss.getShowId());
                        ticketStore.setTicketGearId(Long.parseLong(seatsGearId));
                        ticketStore.setStoreNum(ticketStore.getStoreNum()+storeChange);
                        ticketStoreService.updateById(ticketStore);
                    }
                    showSeatsService.updateById(ss);
                    if(ss.getStatus() == 3 ){
                        List<String> recordKey = new ArrayList<>();
                        recordKey.add(ss.getId()+"");
                        ExchangeCodeRequest exchangeCodeRequest = ExchangeCodeRequest.builder()
                                .businessKey(ss.getId()+"")
                                .recordBusinessKeyList(recordKey)
                                .businessTag(ExchangeCodeBizType.FOR_SEAT_TICKET.getCode())
                                .number(1).build();
                        exchangeCodeMainService.generateCommonExchangeCode(Long.parseLong("1"),exchangeCodeRequest);
                    }
                }
            }
            return responseDto.successData("批量生成兑换码座位成功");
        } catch (BizException e) {
            log.error("批量生成兑换码座位异常,原因：{}",  e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("批量生成兑换码座位异常",  e);
            return responseDto.failSys();
        }
    }
    //删除座位模板行接口
    @Transactional
    @PostMapping("/deleteSeatList")
    @CommonExHandler(key = "删除座位")
    public ResponseDto<String> deleteSeatList(ShowSeatsQuery sq) {
        ResponseDto<String> responseDto = ResponseDto.successDto();
        try {
            if(sq.getLineNo()== null){
                throw new BizException(BizCode.PARAM_NULL.getMessage());
            }
            if(sq.getAreaId()== null){
                throw new BizException(BizCode.PARAM_NULL.getMessage());
            }
            //查询座位状态，如果有售出，禁止编辑座位
            List<ShowSeats> tempList=showSeatsService.findByCondition(sq);
            if (tempList.size()>0){
                throw new BizException(BizCode.BIZ_1032.getMessage());
            }
            SeatsQuery seatsq = new SeatsQuery();
            seatsq = BeanCopyUtil.copyProperties(sq,seatsq.getClass());
            seatsq.setId(null);
            List<Seats> sList=seatsService.findByCondition(seatsq);
            for(Seats ss:sList){
                if(seatsService.deleteById(ss.getId())<=0)
                    throw new BizException(BizCode.BIZ_1032.getMessage());
            }

            return responseDto.successData("删除座位成功");
        } catch (BizException e) {
            log.error("删除座位异常,原因：{}",  e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("删除座位异常",  e);
            return responseDto.failSys();
        }
    }
    //选座接口 undone，移动service层

/*    @PostMapping("/saveSeatList")
    public ResponseDto<String> saveSeatList(@RequestHeader("token") String token ,String seatsListJson) {
        ResponseDto<String> responseDto = ResponseDto.successDto();
        try {
            List<Seats> seatsList= JSON.parseArray(seatsListJson,Seats.class);
            if(seatsList.size()<=0){
                throw new BizException(BizCode.PARAM_NULL.getMessage());
            }
            for (Seats s:seatsList){
                Seats querySeat = seatsService.findById(s.getId());
                if (querySeat.getStatus().equals(1)
                        && redisUtils.setnx(querySeat.getPerformanceId()+"_"+querySeat.getId(),
                        "1",2000)){
                    continue;
                }else{
                    throw new BizException(BizCode.BIZ_1030.getMessage());
                }
            }
            if(seatsService.updateByIds(seatsList) <= 0){
                throw new BizException(BizCode.BIZ_1030.getMessage());
            }
            return responseDto.successData("选座成功");
        } catch (BizException e) {
            log.error("保存选座结果异常,原因：{}",  e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("保存选座结果异常",  e);
            return responseDto.failSys();
        }
    }*/


}
