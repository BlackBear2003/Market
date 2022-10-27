package com.wzl.market.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzl.market.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<com.wzl.market.pojo.User> {
    List<String> getAuthByUserName(String username);

    int setRole(@Param("user_id") int user_id,@Param("role_id") int role_id);
}
