package com.wzl.market.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzl.market.dao.OrderMapper;
import com.wzl.market.dao.RefundRequestMapper;
import com.wzl.market.dao.StoreMapper;
import com.wzl.market.pojo.Order;
import com.wzl.market.pojo.RefundRequest;
import com.wzl.market.service.RefundService;
import com.wzl.market.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class RefundServiceImpl implements RefundService {
    @Autowired
    RefundRequestMapper refundRequestMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    StoreMapper storeMapper;

    @Override
    public ResponseResult getAllByStoreId(int store_id, int current, int size) {

        List<Integer> orderIdList = orderMapper.getOrderIdListByStoreId(store_id);

        QueryWrapper<RefundRequest> wrapper = new QueryWrapper<>();
        wrapper.in("order_id",orderIdList);
        Page<RefundRequest> page= new Page<>(current,size);
        IPage<RefundRequest> iPage = refundRequestMapper.selectPage(page,wrapper);
        long total = iPage.getTotal();
        List<RefundRequest> list = iPage.getRecords();
        return new ResponseResult(200,"total:"+total,list);
    }

    @Override
    public ResponseResult getRefundInfo(int refund_id) {
        RefundRequest refundRequest = refundRequestMapper.selectById(refund_id);
        return new ResponseResult(200,"success",refundRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult storeSetRefundStatus(int refund_id) {
        RefundRequest refundRequest = refundRequestMapper.selectById(refund_id);
        refundRequest.setConfirmTime(new Date(System.currentTimeMillis()));
        if(refundRequest.getRefundStatus()!=0){
            return new ResponseResult(HttpStatus.BAD_REQUEST.value(), "不能修改");
        }
        refundRequest.setIsAgree(1);
        refundRequest.setRefundStatus(1);
        refundRequestMapper.updateById(refundRequest);
        return new ResponseResult(200,"success");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult userSetRefundStatus(int refund_id) {
        RefundRequest refundRequest = refundRequestMapper.selectById(refund_id);
        refundRequest.setRefundTime(new Date(System.currentTimeMillis()));
        if(refundRequest.getRefundStatus()!=1){
            return new ResponseResult(HttpStatus.BAD_REQUEST.value(), "不能修改");
        }
        refundRequest.setRefundStatus(2);
        refundRequestMapper.updateById(refundRequest);
        return new ResponseResult(200,"success");
    }

    @Override
    public Boolean isStoreOfRefund(int user_id, int refund_id) {
        RefundRequest refundRequest = refundRequestMapper.selectById(refund_id);
        int orderId=refundRequest.getOrderId();
        Order order = orderMapper.selectById(orderId);
        int store_id=storeMapper.selectStoreIdByUserId(user_id);
        if(order.getStoreId()==store_id){
            return true;
        }
        return false;
    }

    @Override
    public Boolean isBuyerOfRefund(int user_id, int refund_id) {
        RefundRequest refundRequest = refundRequestMapper.selectById(refund_id);
        int orderId=refundRequest.getOrderId();
        Order order = orderMapper.selectById(orderId);
        if(order.getUserId()==user_id){
            return true;
        }
        return false;
    }

    @Override
    public ResponseResult getAllByUserId(int user_id, int current, int size) {

        List<Integer> orderIdList = orderMapper.getOrderIdListByUserId(user_id);

        QueryWrapper<RefundRequest> wrapper = new QueryWrapper<>();
        wrapper.in("order_id",orderIdList);
        Page<RefundRequest> page= new Page<>(current,size);
        IPage<RefundRequest> iPage = refundRequestMapper.selectPage(page,wrapper);
        long total = iPage.getTotal();
        List<RefundRequest> list = iPage.getRecords();
        return new ResponseResult(200,"total:"+total,list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult createRefund(RefundRequest refundRequest) {
        refundRequest.setRefundId(0);
        refundRequest.setRequestTime(new Date(System.currentTimeMillis()));
        refundRequest.setRefundStatus(0);
        refundRequest.setIsAgree(0);
        refundRequestMapper.updateById(refundRequest);
        return new ResponseResult(200,"success");
    }
}
