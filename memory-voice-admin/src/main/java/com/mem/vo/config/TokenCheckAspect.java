package com.mem.vo.config;

import javax.annotation.Resource;

import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.constant.LoginStatus;
import com.mem.vo.common.constant.RedisPrefix;
import com.mem.vo.common.constant.SourceType;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.JsonUtil;
import com.mem.vo.config.annotations.DontCheckLoginStatus;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author litongwei
 * @description:
 * @date 2019/5/31 18:22
 */
@Component
@Aspect
@Slf4j
@Order(100)
public class TokenCheckAspect {

    @Resource
    private TokenService tokenService;

    // 添加了@Ump注解的所有类及其子类的所有方法 或 添加了@Ump注解的方法
    @Pointcut("execution(public * com.mem.vo.controller..*.*(..)) && !execution(* com.mem.vo.controller.check.ExchangeController.exchange(..))")
    public void pointcut() {

    }


    @Around("pointcut()")
    public Object handleControllerMethod(ProceedingJoinPoint joinPoint) throws Throwable {

        Object obj = null;
        Object[] args = joinPoint.getArgs(); // 参数值
        String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames(); // 参数名

        try {
            for (int i = 0; i < args.length; i++) {

                if (argNames[i].equals("token")) {
                    boolean isEffective = tokenService.isTokenEffective((String) args[i], RedisPrefix.TOKEN.getCode());
                    if (!isEffective) {
                        throw new BizException(BizCode.BIZ_1004.getMessage());
                    }
                    MethodSignature methodSign = (MethodSignature)joinPoint.getSignature();
                    DontCheckLoginStatus conf = methodSign.getMethod().getAnnotation(DontCheckLoginStatus.class);

                    //没有标记注解的，需要校验登录状态
                    if(conf==null){
                        //小程序需要判断手机号授权
                        CommonLoginContext context = tokenService.getContextByken((String) args[i]);
                        BizAssert.notNull(context,"登录信息未空");
                        BizAssert.hasText(context.getSourceCode(),"获取来源系统未空");
                        if(SourceType.WX_MINI.getCode().equals(context.getSourceCode())){
                            Integer loginStatus = context.getStatus();
                            BizAssert.notNull(loginStatus,"登录状态为空");
                            if(context.getStatus().equals(LoginStatus.LOGIN_NOT_AUTH.getCode())){
                                ResponseDto responseDto = new ResponseDto();
                                log.error("token 拦截出现小程序未绑定手机号异常，参数：{}", JsonUtil.toJson(context));
                                responseDto.loginNotAuthorize();
                                return responseDto;
                            }
                        }
                    }

                }
            }
            obj = joinPoint.proceed();

        } catch (BizException throwable) {
            obj = handleException(joinPoint, obj, throwable);

        }catch (Exception e) {

            log.error("登录获取 token 系统异常：",e);
            ResponseDto responseDto = new ResponseDto();
            return  responseDto.failSys();
        }

        return obj;

    }


    private ResponseDto<?> handleException(ProceedingJoinPoint pjp, Object result, BizException e) {
        ResponseDto responseDto = new ResponseDto();
        log.error("token 拦截出现业务异常",e);
        responseDto.loginTimeout();
        responseDto.setMessage(e.getMessage());
        return responseDto;
    }

}
