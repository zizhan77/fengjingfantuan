package com.mem.vo.controller.actor;

import com.mem.vo.business.base.model.po.Actor;
import com.mem.vo.business.base.model.po.ActorTirp;
import com.mem.vo.business.base.service.ActorServise;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.dto.PageBean;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/actor"})
public class ActorController {
    private static final Logger log = LoggerFactory.getLogger(ActorController.class);

    @Resource
    private ActorServise actorServise;

    @PostMapping({"/phone/getActorTripList"})
    public ResponseDto<PageBean<ActorTirp>> getPhoneActorTripList(@RequestParam String time, @RequestParam Integer actorid, @RequestParam Integer page, @RequestParam Integer pageSize) {
        ResponseDto<PageBean<ActorTirp>> responseDto = ResponseDto.successDto();
        try {
            BizAssert.notNull(time, BizCode.PARAM_NULL.getMessage());
            PageBean<ActorTirp> ilst = actorServise.getPhoneActorTripList(time, page, pageSize, actorid);
            return responseDto.successData(ilst);
        } catch (Exception e) {
            log.debug("getPhoneActorTripList", e.getMessage());
            responseDto.failData("有问题");
            return responseDto;
        }
    }

    @PostMapping({"/pc/getActorTripList"})
    public ResponseDto<PageBean<ActorTirp>> getActorTripList(ActorTirp actorTirp, @RequestParam Integer page, @RequestParam Integer pageSize) {
        ResponseDto<PageBean<ActorTirp>> responseDto = ResponseDto.successDto();
        try {
            BizAssert.notNull(actorTirp.getActorid(), BizCode.PARAM_NULL.getMessage());
            PageBean<ActorTirp> ilst = actorServise.getActorTripList(actorTirp, page, pageSize);
            return responseDto.successData(ilst);
        } catch (Exception e) {
            log.debug("getActorTripList", e.getMessage());
            responseDto.failData("有问题");
            return responseDto;
        }
    }

    @PostMapping({"/phone/getActorList"})
    public ResponseDto<PageBean<Actor>> getPhoneActorList(@RequestParam Integer page, @RequestParam Integer pageSize) {
        ResponseDto<PageBean<Actor>> responseDto = ResponseDto.successDto();
        try {
            PageBean<Actor> ilst = actorServise.getPhoneActorList(page, pageSize);
            return responseDto.successData(ilst);
        } catch (Exception e) {
            log.debug("getPhoneActorList", e.getMessage());
            responseDto.failData("有问题");
            return responseDto;
        }
    }

    @PostMapping({"/pc/getActorList"})
    public ResponseDto<PageBean<Actor>> getActorList(Actor actor, @RequestParam Integer page, @RequestParam Integer pageSize) {
        ResponseDto<PageBean<Actor>> responseDto = ResponseDto.successDto();
        try {
            PageBean<Actor> ilst = actorServise.getActorList(actor, page, pageSize);
            return responseDto.successData(ilst);
        } catch (Exception e) {
            log.debug("getActorList", e.getMessage());
            responseDto.failData("有问题");
            return responseDto;
        }
    }

    @PostMapping({"/pc/updateActor"})
    public ResponseDto<Integer> updateActor(Actor actor) {
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        try {
            BizAssert.notNull(actor.getId(), BizCode.PARAM_NULL.getMessage());
            Integer count = actorServise.updateActor(actor);
            return responseDto.successData(count);
        } catch (Exception e) {
            log.debug("updateActor", e.getMessage());
            responseDto.failSys();
            return responseDto;
        }
    }

    @PostMapping({"/pc/updateActorTrip"})
    public ResponseDto<Integer> updateActorTrip(ActorTirp actor) {
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        try {
            BizAssert.notNull(actor.getId(), BizCode.PARAM_NULL.getMessage());
            Integer count = actorServise.updateActorTrip(actor);
            return responseDto.successData(count);
        } catch (Exception e) {
            log.debug("updateActorTrip", e.getMessage());
            responseDto.failSys();
            return responseDto;
        }
    }

    @PostMapping({"/pc/deleteActorTrip"})
    public ResponseDto<Integer> deleteActorTrip(ActorTirp actor) {
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        try {
            BizAssert.notNull(actor.getId(), BizCode.PARAM_NULL.getMessage());
            Integer count = actorServise.deleteActorTrip(actor);
            return responseDto.successData(count);
        } catch (Exception e) {
            log.debug("updateActorTrip", e.getMessage());
            responseDto.failSys();
            return responseDto;
        }
    }

    @PostMapping({"/pc/addActorTrip"})
    public ResponseDto<Integer> addActorTrip(ActorTirp actor) {
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        try {
            Integer count = actorServise.addActorTrip(actor);
            return responseDto.successData(count);
        } catch (Exception e) {
            log.debug("updateActorTrip", e.getMessage());
            responseDto.failSys();
            return responseDto;
        }
    }

    @PostMapping({"/pc/deleteActor"})
    public ResponseDto<Integer> deleteActor(Actor actor) {
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        try {
            BizAssert.notNull(actor.getId(), BizCode.PARAM_NULL.getMessage());
            Integer count = actorServise.deleteActor(actor);
            return responseDto.successData(count);
        } catch (Exception e) {
            log.debug("deleteActor", e.getMessage());
            responseDto.failSys();
            return responseDto;
        }
    }

    @PostMapping({"/pc/addActor"})
    public ResponseDto<Integer> addActor(Actor actor) {
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        try {
            Integer count = actorServise.addActor(actor);
            return responseDto.successData(count);
        } catch (Exception e) {
            log.debug("deleteActor", e.getMessage());
            responseDto.failSys();
            return responseDto;
        }
    }
}
