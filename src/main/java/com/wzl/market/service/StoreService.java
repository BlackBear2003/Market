package com.wzl.market.service;

import com.wzl.market.pojo.Store;
import com.wzl.market.utils.ResponseResult;
import org.springframework.transaction.annotation.Transactional;

public interface StoreService {
    public ResponseResult getAll(int current, int size);


    public ResponseResult create(int user_id, Store store);

    public ResponseResult locate(String tempAddress, String tempAddressProvince, String tempAddressCity, String tempAddressArea, double tempAddressLat, double tempAddressLng);

    public ResponseResult delete(int user_id,int store_id);

    public ResponseResult get(int storeId);


    public ResponseResult update(int user_id, Store store);

    public ResponseResult listPublishGoods(int storeId);
    public ResponseResult listUnPublishGoods();


    @Transactional
    int getStoreIdByUserId(int user_id);
}
