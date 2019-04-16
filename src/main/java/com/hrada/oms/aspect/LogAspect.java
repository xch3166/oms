package com.hrada.oms.aspect;

import com.hrada.oms.dao.common.LogRepository;
import com.hrada.oms.model.common.Log;
import com.hrada.oms.model.common.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Created by shin on 2019-03-11.
 */
@Aspect
@Component
public class LogAspect {

    @Autowired
    LogRepository logRepository;

    @Pointcut("execution(public * com.*.*.controller.*.*.*(..))")
    public void log(){}

    /**
     * 记录HTTP请求开始时的日志
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        User user = (User) request.getSession().getAttribute("user");

        Log log = new Log();
        log.setUser(user);
        log.setIp(request.getRemoteAddr());
        log.setUrl(request.getRequestURL().toString());
        log.setType(request.getMethod());
        log.setMethod(joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
        log.setArgs(Arrays.toString(joinPoint.getArgs()));

        logRepository.save(log);
    }

    /**
     * 记录HTTP请求结束时的日志
     */
    @After("log()")
    public void doAfter(){

    }

    /**
     * 获取返回内容
     */
    @AfterReturning(returning = "object",pointcut = "log()")
    public void doAfterReturn(Object object){

    }
}
