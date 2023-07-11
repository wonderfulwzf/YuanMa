package com.wzf.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class WzfAspect {
    @Pointcut("execution(public void com.wzf.service.UserService.jdbcMethod())")
    public void a(){

    }
    @Before("a()")
    public void wzfBefore(JoinPoint joinPoint) {
        System.out.println("对jdbcmethod进行切入");
    }
}
