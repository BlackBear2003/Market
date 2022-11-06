package com.wzl.market.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzl.market.aop.StoreAuthCheck;
import com.wzl.market.aop.StoreGoodCheck;
import com.wzl.market.aop.StoreOrderCheck;
import com.wzl.market.aop.StoreRefundCheck;
import com.wzl.market.pojo.Good;
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

import java.util.List;

@RestController
@RequestMapping("/api/store")
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
    @StoreAuthCheck
    public ResponseResult updateStoreInfo(@PathVariable("store_id")int store_id,@RequestBody Store store){
        return storeService.update(store_id,store);
    }
    @DeleteMapping("/{store_id}")
    @StoreAuthCheck
    public ResponseResult updateStoreInfo(@PathVariable("store_id")int store_id){
        return storeService.delete(store_id);
    }

    @GetMapping("/{store_id}/order")
    @StoreAuthCheck
    public ResponseResult getStoreOrders(@PathVariable("store_id")int store_id,@Param("current")int current, @Param("size")int size ){
        return orderService.getAllByStoreId(store_id, current, size);
    }

    @GetMapping("/{store_id}/order/{order_id}")
    @StoreOrderCheck
    @StoreAuthCheck
    public ResponseResult getOrderInfo(@PathVariable("store_id")int store_id,@PathVariable("order_id")int order_id){
        return orderService.getOrderInfo(order_id);
    }

    @PutMapping("/{store_id}/order/{order_id}")
    public ResponseResult setShippingInfo(@PathVariable("store_id")int store_id, @PathVariable("order_id")int order_id, int shipType, String shippingCompName, String shippingSn){
        return orderService.updateOrderShippingInfo(order_id, shipType, shippingCompName, shippingSn);
    }

    @GetMapping("/{store_id}/refund")
    @StoreAuthCheck
    public ResponseResult getStoreRefund(@PathVariable("store_id")int store_id, int current, int size){
        return refundService.getAllByStoreId(store_id, current, size);
    }

    @GetMapping("/{store_id}/refund/{refund_id}")
    @StoreAuthCheck
    @StoreRefundCheck
    public ResponseResult getRefundInfo(@PathVariable("store_id")int store_id,@PathVariable("refund_id")int refund_id){
        return refundService.getRefundInfo(refund_id);

    }

    @PutMapping("/{store_id}/refund/{refund_id}")
    @StoreAuthCheck
    @StoreRefundCheck
    public ResponseResult setStatusByStore(@PathVariable("store_id")int store_id,@PathVariable("refund_id")int refund_id){
        return refundService.storeSetRefundStatus(refund_id);

    }

    /******************************商品模块**********************************/

    @GetMapping("/{store_id}/good")
    public ResponseResult getAllGood(@PathVariable("store_id")int store_id,@Param("current")int current, @Param("size")int size){
        Page<Good> page = new Page<>(current,size);
        QueryWrapper<Good> wrapper = new QueryWrapper<>();
        wrapper.eq("store_id",store_id);
        IPage<Good> goodIPage = goodService.page(page);
        List<Good> list = goodIPage.getRecords();
        return new ResponseResult(200,"total:"+goodIPage.getTotal(),list);
    }

    @PostMapping("/{store_id}/good")
    @StoreAuthCheck
    public ResponseResult publishGood(@PathVariable("store_id")int store_id,@RequestBody Good good){
        return goodService.putOnGood(store_id,good);
    }

    @GetMapping("/{store_id}/good/{good_id}")
    public ResponseResult getGoodInfo(@PathVariable("store_id")int store_id,@PathVariable("good_id")int good_id){
        return goodService.getGood(good_id);
    }

    @PutMapping("/{store_id}/good/{good_id}")
    @StoreAuthCheck
    @StoreGoodCheck
    public ResponseResult updateGoodInfo(@PathVariable("store_id")int store_id,@PathVariable("good_id")int good_id,
                                        @RequestBody Good good){
        boolean status = goodService.updateById(good);
        if(status)
            return new ResponseResult(200,"success");
        else
            return new ResponseResult(405,"something error");
    }

    @DeleteMapping("/{store_id}/good/{good_id}")
    @StoreAuthCheck
    @StoreGoodCheck
    public ResponseResult deleteGood(@PathVariable("store_id")int store_id,@PathVariable("good_id")int good_id){
        return goodService.deleteGood(good_id);
    }

    /***********************店铺定位    但是感觉好像没有必要做-- *********************/




}
