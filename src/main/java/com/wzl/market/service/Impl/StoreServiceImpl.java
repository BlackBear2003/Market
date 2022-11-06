package com.wzl.market.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzl.market.dao.GoodMapper;
import com.wzl.market.dao.StoreMapper;
import com.wzl.market.pojo.Good;
import com.wzl.market.pojo.Store;
import com.wzl.market.pojo.User;
import com.wzl.market.security.LoginUser;
import com.wzl.market.service.GoodService;
import com.wzl.market.service.StoreService;
import com.wzl.market.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    StoreMapper storeMapper;
    @Autowired
    GoodMapper goodMapper;

    @Override
    public ResponseResult getAll(int current, int size){
        Page<Store> page = new Page<>(current,size);
        IPage<Store> iPage = storeMapper.selectPage(page,null);
        long total = iPage.getTotal();
        List<Store> list = iPage.getRecords();
        return new ResponseResult(200,"total:"+total,list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult create(int user_id, Store store) {
        store.setStoreId(0);
        storeMapper.insert(store);
        storeMapper.insertUserStoreBind(user_id,store.getStoreId());
        return new ResponseResult<>(200,"创建成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult locate(String tempAddress, String tempAddressProvince, String tempAddressCity, String tempAddressArea, double tempAddressLat, double tempAddressLng) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = loginUser.getUser().getUserId();
        int store_id=storeMapper.selectStoreIdByUserId(id);
        Store store = storeMapper.selectById(store_id);
        store.setStoreAddress(tempAddress);
        store.setStoreAddressProvince(tempAddressProvince);
        store.setStoreAddressCity(tempAddressCity);
        store.setStoreAddressArea(tempAddressArea);
        store.setStoreAddressLat(tempAddressLat);
        store.setStoreAddressLng(tempAddressLng);
        storeMapper.updateById(store);
        return new ResponseResult(200,"修改成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult delete(int store_id) {
        storeMapper.deleteById(store_id);

        storeMapper.deleteUserStoreBindByStoreId(store_id);
        return new ResponseResult(200,"删除成功");
    }

    @Override
    public ResponseResult get(int storeId) {
        Store store = storeMapper.selectById(storeId);
        return new ResponseResult(200,"查询成功",store);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult update(int store_id, Store store) {
        store.setStoreId(store_id);
        storeMapper.updateById(store);
        return new ResponseResult(200,"修改成功");
    }

    @Override
    public ResponseResult listPublishGoods(int storeId) {
        List<Integer> idList = storeMapper.getGoodIdByStoreId(storeId);
        QueryWrapper<Good> wrapper=new QueryWrapper<>();
        wrapper.in("good_id",idList);
        wrapper.eq("publish_status",1);
        List<Good> list = goodMapper.selectList(wrapper);
        return new ResponseResult<>(200,"查询成功",list);
    }

    @Override
    public ResponseResult listUnPublishGoods() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = loginUser.getUser().getUserId();
        int storeId=storeMapper.selectStoreIdByUserId(id);
        List<Integer> idList = storeMapper.getGoodIdByStoreId(storeId);
        QueryWrapper<Good> wrapper=new QueryWrapper<>();
        wrapper.in("good_id",idList);
        wrapper.eq("publish_status",0);
        List<Good> list = goodMapper.selectList(wrapper);
        return new ResponseResult<>(200,"查询成功",list);
    }

    @Override
    public int getStoreIdByUserId(int user_id){
        return storeMapper.selectStoreIdByUserId(user_id);
    }

}
