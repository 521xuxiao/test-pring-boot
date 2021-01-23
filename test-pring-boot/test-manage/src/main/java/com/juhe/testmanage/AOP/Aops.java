package com.juhe.testmanage.AOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class Aops {
    //    Bean   //  指定到某个类上面
     @Around("within(com.juhe.testmanage.service.impl.ToyServiceImpl)")  // AOP到某个类上
    //    @Around("execution(* com.lhdqTest.service.impl.UserServiceImpl.roleGetUser())")  // AOP到某个方法
    //    
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try{
            System.err.println("开始");
            Object list = proceedingJoinPoint.proceed();  // 调用下一个切面或者目标方法
            System.err.println("结束");
            return list;
        }catch(Exception e) {
            e.getMessage();
            throw e;
        }
    }
}
