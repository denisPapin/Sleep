package com.dp.sleep.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggerAspect {

    @Pointcut("bean(*Controller)")
    public void controllers() {
    }

    @Around("controllers()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Вызов: {}.{}() с аргументами: {}",
                joinPoint.getSignature().getDeclaringType(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs())
        );
        Object result = null;
        try {
            result = joinPoint.proceed();
            log.info("Выход: {},{}() с результатом = {}",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    result);
        } catch (Exception e) {
            log.error("Exception: " + e.getMessage());
            result = joinPoint.proceed();
        }
        return result;
    }
}
