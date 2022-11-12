package com.wzl.market.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzl.market.aop.UserAuthCheck;
import com.wzl.market.pojo.GoodComment;
import com.wzl.market.pojo.Order;
import com.wzl.market.pojo.RefundRequest;
import com.wzl.market.security.LoginUser;
import com.wzl.market.service.Impl.GoodCommentServiceImpl;
import com.wzl.market.service.Impl.OrderServiceImpl;
import com.wzl.market.service.Impl.RefundServiceImpl;
import com.wzl.market.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/trade")
public class TradeController {
    @Autowired
    OrderServiceImpl orderService;
    @Autowired
    GoodCommentServiceImpl goodCommentService;
    @Autowired
    RefundServiceImpl refundService;

    @GetMapping("/order")
    @UserAuthCheck
    public ResponseResult getAllOrdersByUser(int id,int current,int size){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",id);
        Page<Order> page = new Page<>(current,size);
        IPage<Order> iPage = orderService.page(page,wrapper);
        List<Order> list = iPage.getRecords();
        return new ResponseResult(200,"success",list);
    }

    @PostMapping("/order")
    public ResponseResult createNewOrder(@RequestBody Order order){
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int login_id = loginUser.getUser().getUserId();
        if(login_id==order.getUserId()){
            return orderService.createOrder(order);
        }
        else{
            return new ResponseResult(401,"权限不足，ID不符");
        }
    }

    @GetMapping("/order/{order_id}")
    public ResponseResult getOrderInfo(@PathVariable int order_id){
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int login_id = loginUser.getUser().getUserId();
        if(orderService.isBuyerOfOrder(login_id,order_id))
            return orderService.getOrderInfo(order_id);
        else
            return new ResponseResult(401,"权限不足，ID不符");
    }

    @PutMapping("/order/{order_id}")
    public ResponseResult updateOrder(@PathVariable int order_id,int shipType, String shippingCompName,
                                      String shippingSn){
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int login_id = loginUser.getUser().getUserId();
        if(orderService.isBuyerOfOrder(login_id,order_id))
            return orderService.updateOrderShippingInfo(order_id,shipType,shippingCompName,shippingSn);
        else
            return new ResponseResult(401,"权限不足，ID不符");
    }

    @PostMapping("/order/{order_id}/comment")
    public ResponseResult doComment(@PathVariable int order_id,@RequestBody GoodComment goodComment) {
        goodComment.setOrderId(order_id);
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int login_id = loginUser.getUser().getUserId();
        if (orderService.isBuyerOfOrder(login_id, order_id)) {
            if (goodCommentService.saveOrUpdate(goodComment))
                return new ResponseResult(200, "success");
            else{
                return new ResponseResult(500,"内部错误");
            }
        }
        else
            return new ResponseResult(401, "权限不足，ID不符");

    }

    @GetMapping("/refund")
    @UserAuthCheck
    public ResponseResult getAllRefundByUser(int id,int current,int size){
        QueryWrapper<RefundRequest> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",id);
        Page<RefundRequest> page = new Page<>(current,size);
        IPage<RefundRequest> iPage = refundService.page(page,wrapper);
        List<RefundRequest> list = iPage.getRecords();
        return new ResponseResult(200,"success",list);
    }

    @PostMapping("/refund")
    public ResponseResult createNewRefund(@RequestBody RefundRequest refundRequest){
        refundRequest.setRefundStatus(0);
        refundRequest.setRequestTime(new Date(System.currentTimeMillis()));
        refundService.saveOrUpdate(refundRequest);
        return new ResponseResult(200,"success");
    }

    @GetMapping("/refund/{refund_id}")
    public ResponseResult getRefundInfo(@PathVariable int refund_id) {
        return refundService.getRefundInfo(refund_id);
    }

    @PutMapping("/refund/{refund_id}")
    public ResponseResult updateRefund(@PathVariable int refund_id){
        return refundService.userSetRefundStatus(refund_id);
    }


}
