package com.wzl.market.service;

import com.wzl.market.pojo.Order;
import com.wzl.market.pojo.RefundRequest;
import com.wzl.market.utils.ResponseResult;

public interface RefundService {
    public ResponseResult getAllByStoreId(int store_id, int current, int size);
    public ResponseResult getRefundInfo(int refund_id);
    public ResponseResult storeSetRefundStatus(int refund_id);
    public ResponseResult userSetRefundStatus(int refund_id);
    public Boolean isStoreOfRefund(int user_id,int refund_id);
    public Boolean isBuyerOfRefund(int user_id,int refund_id);

    public ResponseResult getAllByUserId(int user_id,int current,int size);
    public ResponseResult createRefund(RefundRequest refundRequest);
}
