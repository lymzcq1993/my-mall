package com.hujian.mall.authcenter.service;

import cn.hutool.core.util.StrUtil;
import com.hujian.mall.mapper.ums.mapper.UmsAdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
*@author hujian
*@since 2022-07-26 16:16
*/
@Component
public class MallUserDetailService implements UserDetailsService {
    @Autowired
    UmsAdminMapper umsAdminMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StrUtil.equals(username,"admin")){
            Set<GrantedAuthority> roleSet = new HashSet<>();
            roleSet.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            User user = new User("admin", "111111", roleSet);
            return user;
        }
        if (StrUtil.equals(username,"hujian")){
            Set<GrantedAuthority> roleSet = new HashSet<>();
            roleSet.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            User user = new User("hujian", "222222", roleSet);
            return user;
        }
        return null;
    }
}
