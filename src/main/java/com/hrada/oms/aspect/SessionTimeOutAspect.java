package com.hrada.oms.aspect;

import com.hrada.oms.model.common.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by shin on 2019-03-11.
 */

@Aspect
@Component
public class SessionTimeOutAspect {

    public SessionTimeOutAspect() {
    }
    @Pointcut("execution(public * com.hrada.oms.controller..*.list(..))")
    public void controllerPointcut(){
    }
    @Pointcut("execution(public * com.hrada.oms.controller.MainController.*(..))")
    public void rootPointcut(){//登录登出功能不需要Session验证
    }
    @Pointcut("controllerPointcut()&&(!rootPointcut())")
    public void sessionTimeOutPointcut(){
    }
    @Around("sessionTimeOutPointcut()")
    public Object sessionTimeOutAdvice(ProceedingJoinPoint pjp){
        Object result = null;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        User user = (User) request.getSession().getAttribute("user");
        if(user!=null){
            try {
                result = pjp.proceed();
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return result;
        } else{
            result = "/login";
        }
        return result;
    }
}
