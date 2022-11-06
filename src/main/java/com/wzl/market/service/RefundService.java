package com.wzl.market.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzl.market.pojo.Order;
import com.wzl.market.pojo.RefundRequest;
import com.wzl.market.utils.ResponseResult;

public interface RefundService extends IService<RefundRequest> {
    public ResponseResult getAllByStoreId(int store_id, int current, int size);
    public ResponseResult getRefundInfo(int refund_id);
    public ResponseResult storeSetRefundStatus(int refund_id);
    public ResponseResult userSetRefundStatus(int refund_id);

    public ResponseResult getAllByUserId(int user_id,int current,int size);
    public ResponseResult createRefund(RefundRequest refundRequest);
}
