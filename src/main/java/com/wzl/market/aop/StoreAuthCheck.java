package com.wzl.market.aop;

import org.aspectj.lang.annotation.Pointcut;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StoreAuthCheck {
}
