package com.seahorse.youliao.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.security
 * @ClassName: JwtUserDetails
 * @Description: 安全用户模型
 * 用户认证信息
 * UserDetailsService 加载好用户认证信息后会封装认证信息到一个 UserDetails 的实现类。
 * 默认实现是 User 类，我们这里没有特殊需要，简单继承即可，复杂需求可以在此基础上进行拓展
 * @author:songqiang
 * @Date:2020-01-10 11:18
 **/
public class JwtUserDetails extends User {


    private static final long serialVersionUID = 1L;

    public JwtUserDetails(String username, String password,boolean enabled, Collection<? extends GrantedAuthority> authorities) {
        this(username, password, enabled, true, true, true, authorities);
    }

    public JwtUserDetails(String username, String password, boolean enabled, boolean accountNonExpired,
                          boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

}
