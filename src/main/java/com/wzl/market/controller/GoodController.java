package com.wzl.market.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzl.market.pojo.GoodComment;
import com.wzl.market.service.Impl.GoodCommentServiceImpl;
import com.wzl.market.service.Impl.GoodServiceImpl;
import com.wzl.market.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/good")
public class GoodController {
    @Autowired
    GoodServiceImpl goodService;
    @Autowired
    GoodCommentServiceImpl goodCommentService;

    @GetMapping("/{good_id}")
    public ResponseResult getGoodInfo(@PathVariable("good_id")int good_id){
        return goodService.getGood(good_id);
    }


    @GetMapping("/{good_id}/comment")
    public ResponseResult getGoodComment(@PathVariable int good_id,int current,int size){
        Page<GoodComment> page = new Page<>(current,size);
        QueryWrapper<GoodComment> wrapper = new QueryWrapper<>();
        IPage<GoodComment> iPage = goodCommentService.page(page,wrapper);
        List<GoodComment> list = iPage.getRecords();
        return new ResponseResult(200,"success",list);
    }

}
