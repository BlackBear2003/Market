package com.wzl.market.controller;

import com.alibaba.fastjson.JSONObject;
import com.wzl.market.messaging.SendMsgService;
import com.wzl.market.pojo.ChatMessage;
import com.wzl.market.utils.JsonUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.Duration;

@RestController
public class MessagingController {
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    SendMsgService sendMsgService;


    @MessageMapping("/chat.sendToOne")
    public void sendChatMsgToOne(@Payload ChatMessage chatMessage){
        redisTemplate.opsForValue().set("WebSocket.chat."+chatMessage.getSenderId(),chatMessage.getReceiverId());
        //redisTemplate.expire("WebSocket.chat."+chatMessage.getSenderId(), Duration.ofSeconds(120));
        rabbitTemplate.convertAndSend("chatModuleExchange","message.chat",JsonUtil.parseObjToJson(chatMessage));
    }

    @MessageMapping("/chat.beginChatWithOne.{receiver_id}")
    public void beginChatWithOne(Principal principal, @DestinationVariable String receiver_id){
        redisTemplate.opsForValue().set("WebSocket.chat."+principal.getName(),receiver_id);
        //redisTemplate.expire("WebSocket.chat."+principal.getName(), Duration.ofSeconds(120));
    }
    @GetMapping("/chat/history")
    public void getHistory( int sender_id, int receiver_id, int current, int size) {
        sendMsgService.getUnreadChatMsgWithOne(sender_id, receiver_id, current, size);
    }

    @MessageMapping("/chat.endChatWithOne.{receiver_id}")
    public void endChatWithOne(Principal principal,@DestinationVariable String receiver_id){
        if(redisTemplate.opsForValue().get("WebSocket.chat."+principal.getName()).equals(receiver_id)){
            redisTemplate.delete("WebSocket.chat."+principal.getName());
        }
    }

    @MessageMapping("/public.online")
    public void noticeOnline(Principal principal){
        redisTemplate.opsForSet().add("WebSocket.online",principal.getName(),Duration.ofSeconds(15*60));
    }

    @MessageMapping("/public.offline")
    public void noticeOffline(Principal principal){
        redisTemplate.opsForSet().remove("WebSocket.online",principal.getName());
    }


}
