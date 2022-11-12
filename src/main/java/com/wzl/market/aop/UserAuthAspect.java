package com.wzl.market.aop;

import com.wzl.market.security.LoginUser;
import com.wzl.market.utils.ResponseResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class UserAuthAspect {

    @Pointcut("@annotation(com.wzl.market.aop.UserAuthCheck)")
    public void check(){}

    @Around("check() && args(id,..)")
    public Object around(ProceedingJoinPoint pjp,int id) throws Throwable {
        /*先拿到Request请求体
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        StringBuffer url = request.getRequestURL();
         */

        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int login_id = loginUser.getUser().getUserId();

        if(id==login_id){
            return pjp.proceed();
        }
        else{
            return new ResponseResult<>(401,"权限不足");
        }
    }
}
