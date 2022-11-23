package com.wzl.market.config;

import com.wzl.market.security.LoginUser;
import com.wzl.market.utils.JwtUtil;
import com.wzl.market.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Component
public class AuthChannelInterceptor implements ChannelInterceptor {

    @Autowired
    RedisCache redisCache;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        //1、判断是否首次连接
        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            //2、判断token
            List<String> nativeHeader = accessor.getNativeHeader("token");
            if (nativeHeader != null && !nativeHeader.isEmpty()) {
                String token = nativeHeader.get(0);
                if (StringUtils.isNotBlank(token)) {
                    //todo,通过token获取用户信息，下方用loginUser来代替

                    //2解析token
                    String userId;
                    try {
                        Claims claims = JwtUtil.parseJWT(token);
                        userId = claims.getSubject();
                    } catch (Exception e) {
                        throw new RuntimeException("token不合法！");
                    }

                    //3获取userId, redis获取用户信息
                    LoginUser loginUser = redisCache.getCacheObject("login:" + userId);
                    if (Objects.isNull(loginUser)) {
                        throw new RuntimeException("当前用户未登录！");
                    }


                    if (loginUser != null) {
                        //如果存在用户信息，将用户名赋值，后期发送时，可以指定用户名即可发送到对应用户
                        Principal principal = new Principal() {
                            @Override
                            public String getName() {
                                return ""+loginUser.getUser().getUserId();
                            }
                        };
                        accessor.setUser(principal);
                        return message;
                    }
                }
            }
            return null;
        }
        //不是首次连接，已经登陆成功
        return message;
    }

}
