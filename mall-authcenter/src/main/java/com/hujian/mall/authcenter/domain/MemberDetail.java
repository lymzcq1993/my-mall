package com.hujian.mall.authcenter.domain;

import cn.hutool.core.collection.ListUtil;
import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.hujian.mall.mapper.ums.entity.UmsAdmin;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author hujian
 * @since 2022-07-28 10:27
 */
@Getter
@ToString
public class MemberDetail extends User {

    private final UmsAdmin umsAdmin;

    public MemberDetail(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities
    ,UmsAdmin umsAdmin) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.umsAdmin = umsAdmin;
    }

    public MemberDetail(UmsAdmin umsAdmin){
        super(umsAdmin.getUsername()
                ,umsAdmin.getPassword()
                ,umsAdmin.getStatus() == 1
                ,true
                ,true
                ,true
                ,ListUtil.toList(new SimpleGrantedAuthority("admin")));
        this.umsAdmin = umsAdmin;
    }

}
