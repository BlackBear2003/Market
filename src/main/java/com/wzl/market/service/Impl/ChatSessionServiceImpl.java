package com.wzl.market.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzl.market.dao.ChatSessionMapper;
import com.wzl.market.pojo.ChatSession;
import com.wzl.market.service.ChatSessionService;
import org.springframework.stereotype.Service;

@Service
public class ChatSessionServiceImpl extends ServiceImpl<ChatSessionMapper, ChatSession> implements ChatSessionService {
}
