package com.wzl.market.config;

import com.alibaba.fastjson.support.spring.FastjsonSockJsMessageCodec;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;

import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;

import org.springframework.web.socket.config.annotation.*;



@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Autowired
    AuthChannelInterceptor authChannelInterceptor;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*")
                .withSockJS().setClientLibraryUrl("https://cdn.bootcss.com/sockjs-client/1.1.4/sockjs.min.js")
                .setMessageCodec(new FastjsonSockJsMessageCodec());
        registry.addEndpoint("/alone") .setAllowedOrigins("*")
//                .setHandshakeHandler(new CustomHandshakeHandler())
               .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
      //  registry.setApplicationDestinationPrefixes("/app");

        // Enables a simple in-memory broker
  //   registry.enableSimpleBroker("/topic","/user");

       // registry.setUserDestinationPrefix("/user");

        // 使用RabbitMQ做为消息代理，替换默认的Simple Broker
        //定义了服务端接收地址的前缀，也即客户端给服务端发消息的地址前缀,@SendTo(XXX) 也可以重定向
         registry.setApplicationDestinationPrefixes("/app");
        // "STOMP broker relay"处理所有消息将消息发送到外部的消息代理
        registry.enableStompBrokerRelay("/exchange","/topic","/queue","/amq/queue")
                .setVirtualHost("/")
                .setRelayHost("127.0.0.1")
                .setClientLogin("guest")
                .setClientPasscode("guest")
                .setSystemLogin("guest")
                .setSystemPasscode("guest")
                .setSystemHeartbeatSendInterval(5000)
                .setSystemHeartbeatReceiveInterval(4000);

    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(authChannelInterceptor);
    }

}
