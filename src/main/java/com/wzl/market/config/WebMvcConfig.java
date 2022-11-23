package com.wzl.market.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 请求路径添加统一前缀
     *
     * @param configurer
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        //configurer.addPathPrefix("/api", c -> c.isAnnotationPresent(RestController.class) || c.isAnnotationPresent(Controller.class));
    }


}
