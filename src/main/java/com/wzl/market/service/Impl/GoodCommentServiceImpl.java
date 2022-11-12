package com.wzl.market.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzl.market.dao.GoodCommentMapper;
import com.wzl.market.pojo.GoodComment;
import com.wzl.market.service.GoodCommentService;
import org.springframework.stereotype.Service;

@Service
public class GoodCommentServiceImpl extends ServiceImpl<GoodCommentMapper, GoodComment> implements GoodCommentService {
}
