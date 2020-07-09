package com.seahorse.youliao.security;

import com.seahorse.youliao.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.security
 * @ClassName: JwtAuthenticationFilter
 * @Description: 登录认证检查过滤器
 *
 * 访问接口的时候，登录认证检查过滤器 JwtAuthenticationFilter 会拦截请求校验令牌和登录状态，并根据情况设置登录状态。
 * @author:songqiang
 * @Date:2020-01-10 10:03
 **/
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {


    @Autowired
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 获取token, 并检查登录状态
        SecurityUtils.checkAuthentication(request);
        chain.doFilter(request, response);
    }
}
