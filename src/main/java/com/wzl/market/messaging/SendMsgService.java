package com.wzl.market.messaging;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzl.market.pojo.ChatMessage;
import com.wzl.market.pojo.ChatSession;
import com.wzl.market.service.Impl.ChatMessageServiceImpl;
import com.wzl.market.service.Impl.ChatSessionServiceImpl;
import com.wzl.market.service.Impl.NotifyMessageServiceImpl;
import com.wzl.market.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class SendMsgService {
    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;
    @Autowired
    private ChatMessageServiceImpl chatMessageService;
    @Autowired
    private ChatSessionServiceImpl chatSessionService;
    @Autowired
    private NotifyMessageServiceImpl notifyMessageService;
    @Autowired
    private RedisTemplate redisTemplate;



    public Boolean sendChatMsg(ChatMessage chatMessage){
        try {
            Integer receiverId =chatMessage.getReceiverId();
            Integer senderId = chatMessage.getSenderId();
            if(redisTemplate.opsForValue().get("WebSocket.chat."+receiverId)==senderId){//对面正在和你对hua
                chatMessage.setChatMessageStatus(1);//设为已读
                chatMessageService.save(chatMessage);
                simpMessageSendingOperations.convertAndSend("/chat/"+receiverId,chatMessage);

                UpdateWrapper<ChatSession> wrapper = new UpdateWrapper<>();
                wrapper.eq("host_id",senderId).eq("guest_id",receiverId);
                wrapper.set("last_chat_id",chatMessage.getChatMessageId());
                chatSessionService.update(wrapper);
                UpdateWrapper<ChatSession> wrapper1 = new UpdateWrapper<>();
                wrapper.eq("host_id",receiverId).eq("guest_id",senderId);
                wrapper.set("last_chat_id",chatMessage.getChatMessageId());
                chatSessionService.update(wrapper1);

            }else{
                chatMessage.setChatMessageStatus(0);//设为未读
                chatMessageService.save(chatMessage);

                UpdateWrapper<ChatSession> wrapper = new UpdateWrapper<>();
                wrapper.eq("host_id",senderId).eq("guest_id",receiverId);
                wrapper.set("last_chat_id",chatMessage.getChatMessageId());
                chatSessionService.update(wrapper);
                UpdateWrapper<ChatSession> wrapper1 = new UpdateWrapper<>();
                wrapper.eq("host_id",receiverId).eq("guest_id",senderId);
                wrapper.set("last_chat_id",chatMessage.getChatMessageId()).setSql(" unread_count = unread_count + 1 ");
                chatSessionService.update(wrapper1);
            }
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    @Transactional
    public Boolean getUnreadChatMsgWithOne(int hostId,int guestId,int current,int size){
        Page<ChatMessage> page = new Page<>(current,size);
        QueryWrapper<ChatMessage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sender_id",guestId).eq("receiver_id",hostId).or()
                .eq("sender_id",hostId)
                .eq("receiver_id",guestId);
        queryWrapper.orderByDesc("create_date");
        IPage<ChatMessage> iPage = chatMessageService.page(page,queryWrapper);
        List<ChatMessage> list = iPage.getRecords();
        simpMessageSendingOperations.convertAndSend("/chat/"+hostId,list);
        return true;
    }

}
