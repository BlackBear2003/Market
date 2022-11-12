package com.wzl.market.aop;

import com.wzl.market.dao.OrderMapper;
import com.wzl.market.dao.RefundRequestMapper;
import com.wzl.market.utils.ResponseResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class StoreRefundAspect {
    @Autowired
    RefundRequestMapper refundRequestMapper;
    @Autowired
    OrderMapper orderMapper;

    @Pointcut("@annotation(com.wzl.market.aop.StoreRefundCheck)")
    public void check() {
    }

    @Around("check() && args(store_id,refund_id,..)")
    public Object around(ProceedingJoinPoint pjp, int store_id, int refund_id) throws Throwable {
        /*先拿到Request请求体
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        StringBuffer url = request.getRequestURL();
         */
        int authOrderId = refundRequestMapper.selectById(refund_id).getOrderId();

        int authStoreId = orderMapper.selectById(authOrderId).getStoreId();

        if (authStoreId == store_id) {
            return pjp.proceed();
        } else {
            return new ResponseResult<>(401, "权限不足");
        }
    }
}