package com.wzl.market.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzl.market.pojo.Good;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodMapper extends BaseMapper<Good> {
    List<Good> getGoodsByCategoryId(int category_id);

}
