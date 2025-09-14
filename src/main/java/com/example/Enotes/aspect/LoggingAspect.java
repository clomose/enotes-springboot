package com.example.Enotes.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    @Before("execution(* com.example.Enotes.controller..*(..))")
    public void beforeController(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();
        log.info("Calling :: {} :: {}()",className,methodName);
    }

    @After("execution(* com.example.Enotes.controller..*(..))")
    public void AfterController(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();
        log.info("End Calling :: {} :: {}()",className,methodName);
    }
}
