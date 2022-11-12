package com.wzl.market.aop;

import com.wzl.market.dao.StoreMapper;
import com.wzl.market.security.LoginUser;
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
public class StoreAuthAspect {
    @Autowired
    StoreMapper storeMapper;
    @Pointcut("@annotation(com.wzl.market.aop.StoreAuthCheck)")
    public void check(){}

    @Around("check() && args(store_id,..)")
    public Object around(ProceedingJoinPoint pjp,int store_id) throws Throwable {

        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int loginUserId = loginUser.getUser().getUserId();

        int authStoreId = storeMapper.selectStoreIdByUserId(loginUserId);

        if(authStoreId==store_id){
            return pjp.proceed();
        }else {
            return new ResponseResult<>(401, "权限不足");
        }
    }
}
