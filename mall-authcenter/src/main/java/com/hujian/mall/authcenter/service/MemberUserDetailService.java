package com.hujian.mall.authcenter.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hujian.mall.authcenter.domain.MemberDetail;
import com.hujian.mall.mapper.ums.entity.UmsAdmin;
import com.hujian.mall.mapper.ums.mapper.UmsAdminMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
*@author hujian
*@since 2022-07-26 16:16
*/
@Component
@Slf4j
public class MemberUserDetailService implements UserDetailsService {
    @Autowired
    UmsAdminMapper umsAdminMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UmsAdmin admin = umsAdminMapper.selectOne(new LambdaQueryWrapper<UmsAdmin>()
                .eq(UmsAdmin::getUsername, username));
        if (admin == null) {
            log.warn("用户名不存在:{}",username);
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        MemberDetail user = new MemberDetail(admin);
//        User user = new User(admin.getUsername(), admin.getPassword(), admin.getStatus() == 1,
//                true, true, true, ListUtil.toList(new SimpleGrantedAuthority("admin"))
//        );
//        return user;
        return user;
    }
}
