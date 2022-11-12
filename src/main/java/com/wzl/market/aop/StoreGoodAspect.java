package com.wzl.market.aop;

import com.wzl.market.dao.GoodMapper;
import com.wzl.market.dao.StoreMapper;
import com.wzl.market.utils.ResponseResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class StoreGoodAspect {
    @Autowired
    StoreMapper storeMapper;
    @Pointcut("@annotation(com.wzl.market.aop.StoreGoodCheck)")
    public void check(){}

    @Around("check() && args(store_id,good_id,..)")
    public Object around(ProceedingJoinPoint pjp, int store_id, int good_id) throws Throwable {

        int authStoreId = storeMapper.getStoreIdByGoodId(good_id);

        if(authStoreId==store_id){
            return pjp.proceed();
        }else {
            return new ResponseResult<>(401, "权限不足");
        }
    }
}
