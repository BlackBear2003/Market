package com.wzl.market.security;

import com.wzl.market.dao.UserMapper;
import com.wzl.market.pojo.User;
import com.wzl.market.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
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
        Map<String,Object> user_name_map = new HashMap<String, Object>();
        user_name_map.put("user_name",username);
        User user = userMapper.selectByMap(user_name_map).get(0);
        String password  = user.getPassword();
        password = passwordEncoder.encode(password);

        //TODO:做好查询authname的mapper，这边先用硬编码测试
        //List<String> list=new ArrayList<>(Arrays.asList("hello","del"));

        List<String> list=userMapper.getAuthByUserName(username);
        //System.out.println(list);
        User returnUser = new User();
        returnUser.setUserId(user.getUserId());
        returnUser.setUserName(user.getUserName());
        returnUser.setPassword(password);
        return new LoginUser(returnUser//这里的密码是加盐的)
                ,list);

    }
}
