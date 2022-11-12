package com.wzl.market.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzl.market.dao.ChatMessageMapper;
import com.wzl.market.pojo.ChatMessage;
import com.wzl.market.service.ChatMessageService;
import com.wzl.market.utils.ResponseResult;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageServiceImpl extends ServiceImpl<ChatMessageMapper, ChatMessage> implements ChatMessageService {
}
