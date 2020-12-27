package com.mem.vo.config;

import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.JsonUtil;
import com.mem.vo.config.annotations.CommonExHandler;
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
 * @description 通用异常处理
 * @date 2019/6/26 15:14
 */

@Component
@Aspect
@Slf4j
@Order(200)
public class CommonExceptionAspect {

    @Pointcut("@within(com.mem.vo.config.annotations.CommonExHandler) || @annotation(com.mem.vo.config.annotations.CommonExHandler)")
    public void pointcut() {

    }


    @Around("pointcut()")
    public Object handleControllerMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Object obj;
        Object[] args = joinPoint.getArgs(); // 参数值
        String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames(); // 参数名

        MethodSignature methodSign = (MethodSignature)joinPoint.getSignature();
        CommonExHandler conf = methodSign.getMethod().getAnnotation(CommonExHandler.class);
        try {
            obj = joinPoint.proceed();
        } catch (BizException e) {
            StringBuilder stringBuilder = getExMessagePrefix(args,argNames, conf);

            stringBuilder.append("业务异常: 原因：");
            log.error(stringBuilder.toString(),e);
            ResponseDto responseDto = new ResponseDto();
            return  responseDto.failData(e.getMessage());

        }catch (Exception e) {
            StringBuilder stringBuilder = getExMessagePrefix(args,argNames, conf);
            stringBuilder.append("系统异常: 原因：");

            log.error(stringBuilder.toString(),e);
            ResponseDto responseDto = new ResponseDto();
            return  responseDto.failSys();
        }

        return obj;

    }

    private StringBuilder getExMessagePrefix(Object[] args,String[] argNames, CommonExHandler conf) {
        StringBuilder stringBuilder = new StringBuilder(conf.key());
        if(args!=null){
            stringBuilder.append("参数");

            for (int i = 0; i < args.length; i++) {
                stringBuilder.append(argNames[i]);
                stringBuilder.append(" ：");
                stringBuilder.append(JsonUtil.toJson(args[i]));
                stringBuilder.append(" , ");
            }
        }
        return stringBuilder;
    }


}
