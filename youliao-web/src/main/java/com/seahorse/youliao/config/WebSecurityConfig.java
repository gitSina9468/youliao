package com.seahorse.youliao.config;

import com.alibaba.fastjson.JSONObject;
import com.seahorse.youliao.common.ResponseEntity;
import com.seahorse.youliao.security.JwtAuthenticationFilter;
import com.seahorse.youliao.security.JwtAuthenticationProvider;
import com.seahorse.youliao.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.config
 * @ClassName: WebSecurityConfig
 * @Description: 安全配置类
 * 下面这个配置类是Spring Security的关键配置。
 * <p>
 * 在这个配置类中，我们主要做了以下几个配置：
 * <p>
 * 1. 访问路径URL的授权策略，如登录、Swagger访问免登录认证等
 * <p>
 * 2. 指定了登录认证流程过滤器 JwtLoginFilter，由它来触发登录认证
 * <p>
 * 3. 指定了自定义身份认证组件 JwtAuthenticationProvider，并注入 UserDetailsService
 * <p>
 * 4. 指定了访问控制过滤器 JwtAuthenticationFilter，在授权时解析令牌和设置登录状态
 * <p>
 * 5. 指定了退出登录处理器，因为是前后端分离，防止内置的登录处理器在后台进行跳转
 * @author:songqiang
 * @Date:2020-01-10 9:39
 **/

/**
 * @EnableWebSecurity 作用 ：
 * 1: 加载了WebSecurityConfiguration配置类, 配置安全认证策略。在这个配置类中,
 * 注入了一个非常重要的bean, bean的name为: springSecurityFilterChain，
 * 这是Spring Secuity的核心过滤器, 这是请求的认证入口。
 * 2: 加载了AuthenticationConfiguration, 配置了认证信息。
 *  这个类是来配置认证相关的核心类, 这个类的主要作用是,
 *  向spring容器中注入AuthenticationManagerBuilder, AuthenticationManagerBuilder其实是使用了建造者模式,
 *  他能建造AuthenticationManager, 这个类前面提过,是身份认证的入口。
 *
 *  @EnableGlobalMethodSecurity详解：
 *  @EnableGlobalMethodSecurity(securedEnabled=true) 开启@Secured 注解过滤权限
 *  @EnableGlobalMethodSecurity(jsr250Enabled=true)开启@RolesAllowed 注解过滤权限
 *  @EnableGlobalMethodSecurity(prePostEnabled=true) 使用表达式时间方法级别的安全性
 *   4个注解可用：
 *          @PreAuthorize 在方法调用之前, 基于表达式的计算结果来限制对方法的访问
 *          @PostAuthorize 允许方法调用, 但是如果表达式计算结果为false, 将抛出一个安全性异常
 *          @PostFilter 允许方法调用, 但必须按照表达式来过滤方法的结果
 *          @PreFilter 允许方法调用, 但必须在进入方法之前过滤输入值
 *
 * 权限资源 SecurityMetadataSource
 * 权限决策 AccessDecisionManager
 *
 * 针对于url 和 方法注解权限控制：WebSecurityConfigurerAdapter 适配来实现
 * FilterSecurityInterceptor 资源控制
 * MethodSecurityInterceptor 方法注解权限控制
 *
 * 更多springSecurity 相关可以查找相关文章 深入源码了解  深入越深 坑越大 越陷越深 最后你会发现柳暗花明又一村
 * 不知觉中就进步了  要进阶 的适应慢慢看源码
 *  @author:songqiang
 *  @Date:2020-01-14 9:39
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 使用自定义登录身份认证组件
        auth.authenticationProvider(new JwtAuthenticationProvider(userDetailsService));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        项目中用到iframe嵌入网页，然后用到springsecurity就被拦截了 浏览器报错  x-frame-options deny
//        原因是因为springSecurty使用X-Frame-Options防止网页被Frame
        http.headers().frameOptions().disable();
        // 禁用 csrf(Cross-site request forgery)跨站请求伪造, 由于使用的是JWT，我们这里不需要csrf
        //https://blog.csdn.net/yjclsx/article/details/80349906
        //处理来自浏览器的请求需要是CSRF保护，如果后台服务是提供API调用那么可能就要禁用CSRF保护
        http.cors().and().csrf().disable()
                .authorizeRequests()
                // 跨域预检请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 登录URL
                .antMatchers("/mock/**").permitAll()
                .antMatchers("/auth/login").permitAll()
                .antMatchers("/auth/captcha").permitAll()
                .antMatchers("/resume.html").permitAll()
                .antMatchers("/sys/common/**").permitAll()
                .antMatchers("/user/**").permitAll()
                .antMatchers("/pdf/**").permitAll()
                .antMatchers("/generic/**").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/easypoi/**").permitAll()
                //排号大厅
                .antMatchers("/screen/**").permitAll()
                .antMatchers("/screenws/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/assets/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                //文档模板
                .antMatchers("/freemarker/**").permitAll()
                .antMatchers("/qrCode/**").permitAll()
                .antMatchers("/xdoc/**").permitAll()
                .antMatchers("/qrCode/**").permitAll()
                //websocket 日志
                .antMatchers("/log/**").permitAll()
                .antMatchers("/connect/**").permitAll()
                // swagger
                .antMatchers("/swagger**/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/**").permitAll()
                //druid
                .antMatchers("/druid/**").permitAll()
                .antMatchers("/doc.html").permitAll()
                // 其他所有请求需要身份认证
                .anyRequest().authenticated();
                //对于前后分离项目 不推荐使用 原生formLogin()
//                .and().formLogin().loginProcessingUrl("/login")
        //各类错误异常处理 以下针对于访问资源路径 认证异常捕获 和 无权限处理
        http.exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest req, HttpServletResponse resp, AuthenticationException exception) throws IOException, ServletException {
                resp.setContentType("application/json;charset=utf-8");
                PrintWriter out = resp.getWriter();
                ResponseEntity respBean = ResponseEntity.error("认证异常："+exception.getMessage());
                //封装异常描述信息
                String json = JSONObject.toJSONString(respBean);
                out.write(json);
                out.flush();
                out.close();
            }
        }).accessDeniedHandler(new AccessDeniedHandler(){
            @Override
            public void handle(HttpServletRequest resq, HttpServletResponse resp, AccessDeniedException exception) throws IOException, ServletException{
                resp.setContentType("application/json;charset=utf-8");
                PrintWriter out = resp.getWriter();
                ResponseEntity respBean = ResponseEntity.error("无权限："+exception.getMessage());
                String json = JSONObject.toJSONString(respBean);
                out.write(json);
                out.flush();
                out.close();
            }
        });
        // 其他所有请求需要身份认证
        // 退出登录处理器
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
        // 开启登录认证流程过滤器，目前使用LoginController的login接口, 需要注释掉此过滤器，根据使用习惯二选一即可
//        http.addFilterBefore(new JwtLoginFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
        // 访问控制时登录状态检查过滤器
        http.addFilterBefore(new JwtAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
        // session 策略配置：谨慎选择非默认配置，会导致用户信息获取不到之类问题 使用前了解清楚
        //  默认配置 ： SessionCreationPolicy.IF_REQUIRED
//        security.sessions策略如下：
//        always：保存session状态（每次会话都保存，可能会导致内存溢出【Always create an {@link HttpSession}】）
//        never：不会创建HttpSession，但是会使用已经存在的HttpSession[Spring Security will never create an {@link HttpSession}]
//        if_required：仅在需要HttpSession创建【Spring Security will only create an {@link HttpSession} if required】
//        stateless：不会保存session状态【 Spring Security will never create an {@link HttpSession} and it will never use it
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}
