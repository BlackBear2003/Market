package com.wzl.market.aop;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
/**
 * 检测退款是不是属于这个店铺
 */
public @interface StoreRefundCheck {
}
