package com.wzl.market.service;

import com.wzl.market.pojo.Order;
import com.wzl.market.utils.ResponseResult;

public interface OrderService {
    public ResponseResult getAllByStoreId(int store_id,int current,int size);
    public ResponseResult getOrderInfo(int order_id);
    public ResponseResult updateOrderShippingInfo(int order_id,int shipType,String shippingCompName,String shippingSn) throws Exception;
    public ResponseResult confirmReceive(int order_id);
    public Boolean isStoreOfOrder(int user_id,int order_id);
    public Boolean isBuyerOfOrder(int user_id,int order_id);

    public ResponseResult getAllByUserId(int user_id,int current,int size);
    public ResponseResult createOrder(Order order);

}
