package com.wzl.market.service.Impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzl.market.dao.NotifyMessageMapper;
import com.wzl.market.pojo.NotifyMessage;
import com.wzl.market.service.NotifyMessageService;
import org.springframework.stereotype.Service;

@Service
public class NotifyMessageServiceImpl extends ServiceImpl<NotifyMessageMapper, NotifyMessage> implements NotifyMessageService {
}
