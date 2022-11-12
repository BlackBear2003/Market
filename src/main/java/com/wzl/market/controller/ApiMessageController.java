package com.wzl.market.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzl.market.pojo.ChatMessage;
import com.wzl.market.pojo.ChatSession;
import com.wzl.market.pojo.NotifyMessage;
import com.wzl.market.security.LoginUser;
import com.wzl.market.service.Impl.ChatMessageServiceImpl;
import com.wzl.market.service.Impl.ChatSessionServiceImpl;
import com.wzl.market.service.Impl.NotifyMessageServiceImpl;
import com.wzl.market.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/message")
public class ApiMessageController {

    @Autowired
    ChatMessageServiceImpl chatMessageService;
    @Autowired
    ChatSessionServiceImpl chatSessionService;
    @Autowired
    NotifyMessageServiceImpl notifyMessageService;

    @PostMapping("/chat")
    public ResponseResult createNewChat(@RequestBody ChatMessage chatMessage){
        chatMessage.setChatMessageStatus(0);
        chatMessage.setCreateDate(new Date());
        if(chatMessageService.save(chatMessage)){
            return new ResponseResult<>(200,"success");
        }
        else{
            return new ResponseResult(500,"内部错误");
        }
    }

    @GetMapping("/chat/{chat_id}")
    public ResponseResult getByChatId(@PathVariable int chat_id){
        ChatMessage chatMessage = chatMessageService.getById(chat_id);
        return new ResponseResult(200,"success",chatMessage);
    }

    @PutMapping("/chat/{chat_id}")
    public ResponseResult receiveChat(@PathVariable int chat_id){
        UpdateWrapper<ChatMessage> wrapper = new UpdateWrapper<>();
        wrapper.eq("chat_id",chat_id).set("chat_message_status",1);
        if(chatMessageService.update(wrapper))
            return new ResponseResult(200,"success");
        else
            return new ResponseResult(500,"internal failed");
    }

    @PostMapping("/notify")
    public ResponseResult createNotification(@RequestBody NotifyMessage notifyMessage){
        notifyMessage.setCreateDate(new Date());
        notifyMessage.setNotifyMessageStatus(0);
        if(notifyMessageService.save(notifyMessage))
            return new ResponseResult(200,"success");
        else
            return new ResponseResult(500,"something wrong");
    }

    @PostMapping("/session")
    public ResponseResult createSession(int host_id,int guest_id){
        ChatSession session = new ChatSession();
        session.setHostId(host_id);
        session.setGuestId(guest_id);
        chatSessionService.save(session);
        session.setHostId(guest_id);
        session.setGuestId(host_id);
        chatSessionService.save(session);
        return new ResponseResult(200,"OK");
    }

    @GetMapping("/session")
    public ResponseResult getUserSession(int current,int size){
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int user_id = loginUser.getUser().getUserId();
        QueryWrapper<ChatSession> wrapper = new QueryWrapper<>();
        wrapper.eq("host_id",user_id);
        Page<ChatSession> page = new Page<>(current,size);
        IPage<ChatSession> iPage = chatSessionService.page(page,wrapper);
        List<ChatSession> list = iPage.getRecords();
        return new ResponseResult<>(200,"success",list);
    }



}
