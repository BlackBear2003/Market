package com.wzl.market.service.Impl;

import com.wzl.market.pojo.User;
import com.wzl.market.security.LoginUser;
import com.wzl.market.service.LoginService;
import com.wzl.market.utils.JwtUtil;
import com.wzl.market.utils.RedisCache;
import com.wzl.market.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RedisCache redisCache;
    @Override
    public ResponseResult login(User user) {
        //3使用ProviderManager auth方法进行验证
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        //校验失败了
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误！");
        }

        //4自己生成jwt给前端
        LoginUser loginUser= (LoginUser)(authenticate.getPrincipal());
        String userId = String.valueOf(loginUser.getUser().getUserId());
        if(loginUser.getUser().getAuditStatus()==1){
            return new ResponseResult(403,"账号已被封禁");
        }


        String jwt = JwtUtil.createJWT(userId);
        Map<String,String> map=new HashMap();
        map.put("token",jwt);
        //map.put("auth",loginUser.getAuthorities().toString());
        //5系统用户相关所有信息放入redis
        redisCache.setCacheObject("login:"+userId,loginUser);


        return new ResponseResult(200,"登陆成功",map);
    }

    @Override
    public ResponseResult logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        int userId = loginUser.getUser().getUserId();
        redisCache.deleteObject("login:"+userId);

        return new ResponseResult(200,"退出成功！");
    }
}
