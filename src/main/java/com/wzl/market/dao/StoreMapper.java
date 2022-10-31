package com.wzl.market.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzl.market.pojo.Store;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StoreMapper extends BaseMapper<Store> {

    public int selectStoreIdByUserId(int user_id);

    public int insertGoodIdForStore(int store_id,int good_id);

    public int insertUserStoreBind(int user_id,int store_id);
    public int deleteUserStoreBind(int user_id);

    public List<Integer> getGoodIdByStoreId(int store_id);
}
