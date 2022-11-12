package com.wzl.market.service.Impl;

import com.wzl.market.dao.GoodMapper;
import com.wzl.market.dao.StoreMapper;
import com.wzl.market.service.SearchService;
import com.wzl.market.utils.ResponseResult;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    GoodMapper goodMapper;
    @Autowired
    StoreMapper storeMapper;


    @Override
    public ResponseResult search(double lng,double lat,String goodName,double down,double up,int categoryId, int current, int size) {

        HashMap<String,Object> map = new HashMap<>();
        map.put("lng",lng);
        map.put("lat",lat);
        map.put("goodName",goodName);
        map.put("down",down);
        map.put("up",up);
        map.put("categoryId",categoryId);

        List<HashMap<String,Object>> list =  goodMapper.searchGood(map,new RowBounds(current-1,size));

        return new ResponseResult<>(200,"success",list);
    }

}
