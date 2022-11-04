package com.wzl.market.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzl.market.pojo.ChatMessage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatMessageMapper extends BaseMapper<ChatMessage> {
}
