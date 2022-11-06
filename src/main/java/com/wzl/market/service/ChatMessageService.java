package com.wzl.market.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzl.market.pojo.ChatMessage;
import com.wzl.market.utils.ResponseResult;

public interface ChatMessageService extends IService<ChatMessage> {
    public ResponseResult test();
}
