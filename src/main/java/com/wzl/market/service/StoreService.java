package com.wzl.market.service;

import com.wzl.market.pojo.Store;
import com.wzl.market.utils.ResponseResult;

public interface StoreService {
    public ResponseResult create(Store store);

    public ResponseResult locate(String tempAddress,String tempAddressProvince,String tempAddressCity,String tempAddressArea,double tempAddressLat,double tempAddressLng);

    public ResponseResult delete();

    public ResponseResult get(int storeId);

    public ResponseResult update(Store store);

    public ResponseResult listPublishGoods(int storeId);
    public ResponseResult listUnPublishGoods();



}
