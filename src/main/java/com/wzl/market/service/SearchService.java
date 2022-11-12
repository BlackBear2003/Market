package com.wzl.market.service;

import com.wzl.market.utils.ResponseResult;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;

public interface SearchService {
    public ResponseResult search(double lng,double lat,String goodName,double down,double up,int categoryId, int current, int size);

}
