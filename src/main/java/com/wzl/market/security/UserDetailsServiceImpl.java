package com.wzl.market.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzl.market.dao.UserMapper;
import com.wzl.market.pojo.User;
import com.wzl.market.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_name",username);
        User user = userMapper.selectOne(wrapper);
        if(user==null) {
            throw new UsernameNotFoundException("no username found");
        }
        String password  = user.getPassword();
        password = passwordEncoder.encode(password);

        //TODO:做好查询authname的mapper，这边先用硬编码测试
        //List<String> list=new ArrayList<>(Arrays.asList("hello","del"));

        List<String> list=userMapper.getAuthByUserName(username);
        //System.out.println(list);
        user.setPassword(password);
        return new LoginUser(user//这里的密码是加盐的)
                ,list);

    }
}
