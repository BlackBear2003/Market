package com.wzl.market.controller;

import com.wzl.market.pojo.Store;
import com.wzl.market.security.LoginUser;
import com.wzl.market.service.Impl.GoodServiceImpl;
import com.wzl.market.service.Impl.OrderServiceImpl;
import com.wzl.market.service.Impl.RefundServiceImpl;
import com.wzl.market.service.Impl.StoreServiceImpl;
import com.wzl.market.service.OrderService;
import com.wzl.market.utils.ResponseResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/store")
public class StoreController {
    @Autowired
    StoreServiceImpl storeService;
    @Autowired
    GoodServiceImpl goodService;
    @Autowired
    OrderServiceImpl orderService;
    @Autowired
    RefundServiceImpl refundService;


    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping("")
    public ResponseResult getAll(@Param("current")int current, @Param("size")int size ){
        return storeService.getAll(current, size);
    }

    @PostMapping("")
    public ResponseResult createStore(@RequestBody Store store){
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int user_id = loginUser.getUser().getUserId();
        return storeService.create(user_id,store);
    }

    @GetMapping("/{store_id}")
    public ResponseResult getStoreInfo(@PathVariable("store_id")int store_id){
        return storeService.get(store_id);
    }

    @PutMapping("/{store_id}")
    public ResponseResult updateStoreInfo(@PathVariable("store_id")int store_id,@RequestBody Store store){
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int user_id = loginUser.getUser().getUserId();
        int user_store_id = storeService.getStoreIdByUserId(user_id);
        if(user_store_id==store_id||loginUser.getAuthorities().contains("admin")){
            return storeService.update(store_id,store);
        }else{
            return new ResponseResult(401,"权限不足");
        }
    }
    @DeleteMapping("/{store_id}")
    public ResponseResult updateStoreInfo(@PathVariable("store_id")int store_id){
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int user_id = loginUser.getUser().getUserId();
        int user_store_id = storeService.getStoreIdByUserId(user_id);
        if(user_store_id==store_id||loginUser.getAuthorities().contains("admin")){
            return storeService.delete(user_id,store_id);
        }else{
            return new ResponseResult(401,"权限不足");
        }
    }

    @GetMapping("/{store_id}/order")
    public ResponseResult getStoreOrders(@PathVariable("store_id")int store_id,@Param("current")int current, @Param("size")int size ){
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int user_id = loginUser.getUser().getUserId();
        int user_store_id = storeService.getStoreIdByUserId(user_id);
        if(user_store_id==store_id||loginUser.getAuthorities().contains("admin")){
            return orderService.getAllByStoreId(store_id, current, size);
        }
        else{
            return new ResponseResult(401,"权限不足");
        }
    }

    @GetMapping("/{store_id}/order/{order_id}")
    public ResponseResult getOrderInfo(@PathVariable("store_id")int store_id,@PathVariable("order_id")int order_id){
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int user_id = loginUser.getUser().getUserId();
        if(orderService.isStoreOfOrder(user_id,order_id)||loginUser.getAuthorities().contains("admin")){
            return orderService.getOrderInfo(order_id);
        }
        else{
            return new ResponseResult(401,"权限不足");
        }
    }

    @PutMapping("/{store_id}/order/{order_id}")
    public ResponseResult setShippingInfo(@PathVariable("order_id")int order_id, int shipType, String shippingCompName, String shippingSn){
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int user_id = loginUser.getUser().getUserId();
        if(orderService.isStoreOfOrder(user_id,order_id)||loginUser.getAuthorities().contains("admin")){
            return orderService.updateOrderShippingInfo(order_id, shipType, shippingCompName, shippingSn);
        }
        else{
            return new ResponseResult(401,"权限不足");
        }
    }

    @GetMapping("/{store_id}/refund")
    public ResponseResult getStoreRefund(@PathVariable("store_id")int store_id, int current, int size){
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int user_id = loginUser.getUser().getUserId();
        int user_store_id = storeService.getStoreIdByUserId(user_id);
        if(user_store_id==store_id||loginUser.getAuthorities().contains("admin")){
            return refundService.getAllByStoreId(store_id, current, size);
        }else{
            return new ResponseResult(401,"权限不足");
        }
    }

    @GetMapping("/{store_id}/refund/{refund_id}")
    public ResponseResult getRefundInfo(@PathVariable("store_id")int store_id,@PathVariable("refund_id")int refund_id){
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int user_id = loginUser.getUser().getUserId();
        if(refundService.isStoreOfRefund(user_id,refund_id)){
            return refundService.getRefundInfo(refund_id);
        }else{
            return new ResponseResult(401,"权限不足");
        }
    }

    @PutMapping("/{store_id}/refund/{refund_id}")
    public ResponseResult setStatusByStore(@PathVariable("store_id")int store_id,@PathVariable("refund_id")int refund_id){
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int user_id = loginUser.getUser().getUserId();
        if(refundService.isStoreOfRefund(user_id,refund_id)){
            return refundService.storeSetRefundStatus(refund_id);
        }else{
            return new ResponseResult(401,"权限不足");
        }
    }

    //******************************商品模块**********************************


}
