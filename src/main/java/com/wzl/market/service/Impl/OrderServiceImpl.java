package com.wzl.market.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzl.market.dao.OrderMapper;
import com.wzl.market.dao.StoreMapper;
import com.wzl.market.pojo.Order;
import com.wzl.market.service.OrderService;
import com.wzl.market.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    StoreMapper storeMapper;

    @Override
    public ResponseResult getAllByStoreId(int store_id, int current, int size) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("store_id",store_id);
        Page<Order> page= new Page<>(current,size);
        IPage<Order> iPage = orderMapper.selectPage(page,wrapper);
        long total = iPage.getTotal();
        List<Order> list = iPage.getRecords();
        return new ResponseResult(200,"total:"+total,list);
    }

    @Override
    public ResponseResult getOrderInfo(int order_id) {
        Order order = orderMapper.selectById(order_id);
        return new ResponseResult(200,"success",order);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult updateOrderShippingInfo(int order_id, int shipType, String shippingCompName, String shippingSn) {
        Order order = orderMapper.selectById(order_id);
        order.setShipType(shipType);
        order.setShippingCompName(shippingCompName);
        order.setShippingSn(shippingSn);
        order.setShippingTime(new Date(System.currentTimeMillis()));
        if(order.getOrderStatus()!=0){
            return new ResponseResult(HttpStatus.BAD_REQUEST.value(), "不能修改");
        }
        order.setOrderStatus(1);
        orderMapper.updateById(order);
        return new ResponseResult(200,"success");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult confirmReceive(int order_id) {
        Order order = orderMapper.selectById(order_id);
        if(order.getOrderStatus()!=1){
            return new ResponseResult(HttpStatus.BAD_REQUEST.value(), "不能修改");
        }
        order.setOrderStatus(2);
        order.setReceiveTime(new Date(System.currentTimeMillis()));
        return new ResponseResult(200,"success");
    }

    @Override
    public Boolean isStoreOfOrder(int user_id, int order_id) {
        Order order = orderMapper.selectById(order_id);
        int store_id=storeMapper.selectStoreIdByUserId(user_id);
        if(store_id==order.getStoreId()){
            return true;
        }
        return false;
    }

    @Override
    public Boolean isBuyerOfOrder(int user_id, int order_id) {
        Order order = orderMapper.selectById(order_id);
        if(user_id==order.getUserId()){
            return true;
        }
        return false;
    }

    @Override
    public ResponseResult getAllByUserId(int user_id, int current, int size) {

        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",user_id);
        Page<Order> page= new Page<>(current,size);
        IPage<Order> iPage = orderMapper.selectPage(page,wrapper);
        long total = iPage.getTotal();
        List<Order> list = iPage.getRecords();
        return new ResponseResult(200,"total:"+total,list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult createOrder(Order order){
        order.setOrderId(0);
        order.setCreateTime(new Date(System.currentTimeMillis()));
        order.setPayTime(new Date(System.currentTimeMillis()));
        order.setOrderStatus(0);
        return new ResponseResult(200,"success");
    }
}
