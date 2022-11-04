package com.wzl.market.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzl.market.pojo.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    public List<Integer> getOrderIdListByStoreId(int store_id);
    public List<Integer> getOrderIdListByUserId(int user_id);
}
