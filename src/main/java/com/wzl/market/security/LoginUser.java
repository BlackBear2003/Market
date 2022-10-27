package com.wzl.market.security;

import com.alibaba.fastjson.annotation.JSONField;
import com.wzl.market.pojo.User;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class LoginUser implements UserDetails {

    private User user;
    List<String> permissions;

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public User getUser() {
        return user;
    }

    public LoginUser() {
    }

    public void setUser(User user) {
        this.user = user;
    }
    public LoginUser(User user,List<String> permissions) {
        this.user = user;
        this.permissions=permissions;
        //System.out.println(permissions.toString());
    }

    @JSONField(serialize = false)
    List<SimpleGrantedAuthority> authorities;


    public LoginUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        if (authorities!=null){
            return authorities;
        }
        authorities = permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        //System.out.println(authorities.toString());
        return authorities;
    }


    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
