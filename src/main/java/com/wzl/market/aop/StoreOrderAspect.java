package com.wzl.market.aop;

import com.wzl.market.dao.OrderMapper;
import com.wzl.market.security.LoginUser;
import com.wzl.market.service.Impl.OrderServiceImpl;
import com.wzl.market.service.Impl.StoreServiceImpl;
import com.wzl.market.utils.ResponseResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class StoreOrderAspect {
    @Autowired
    OrderMapper orderMapper;

    @Pointcut("@annotation(com.wzl.market.aop.StoreOrderCheck)")
    public void check(){}

    @Around("check() && args(store_id,order_id)")
    public Object around(ProceedingJoinPoint pjp, int store_id,int order_id) throws Throwable {
        /*先拿到Request请求体
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        StringBuffer url = request.getRequestURL();
         */

        int authStoreId = orderMapper.selectById(order_id).getStoreId();

        if(authStoreId==store_id){
            return pjp.proceed();
        }else {
            return new ResponseResult<>(401, "权限不足");
        }
    }

}
