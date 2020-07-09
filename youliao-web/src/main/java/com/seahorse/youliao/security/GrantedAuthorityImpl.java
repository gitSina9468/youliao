package com.seahorse.youliao.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.security
 * @ClassName: GrantedAuthorityImpl
 * @Description: 权限封装
 * @author:songqiang
 * @Date:2020-01-10 11:33
 **/
public class GrantedAuthorityImpl implements GrantedAuthority {


    private static final long serialVersionUID = 1L;

    private String authority;

    public GrantedAuthorityImpl(String authority) {
        this.authority = authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
