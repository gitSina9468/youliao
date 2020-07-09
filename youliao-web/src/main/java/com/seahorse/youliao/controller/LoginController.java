package com.seahorse.youliao.controller;

import com.seahorse.youliao.aop.NoRepeatSubmit;
import com.seahorse.youliao.common.ResponseEntity;
import com.seahorse.youliao.exception.BusinessException;
import com.seahorse.youliao.security.JwtAuthenticatioToken;
import com.seahorse.youliao.service.SysLoginLogService;
import com.seahorse.youliao.service.SysMenuService;
import com.seahorse.youliao.service.SysRoleService;
import com.seahorse.youliao.service.SysUserService;
import com.seahorse.youliao.service.entity.SysLoginLogDTO;
import com.seahorse.youliao.service.entity.SysMenuDTO;
import com.seahorse.youliao.service.entity.SysRoleDTO;
import com.seahorse.youliao.service.entity.SysUserDTO;
import com.seahorse.youliao.util.IpUtil;
import com.seahorse.youliao.util.SecurityUtils;
import com.seahorse.youliao.utils.BeanUtil;
import com.seahorse.youliao.vo.request.LoginRequestVO;
import com.seahorse.youliao.vo.response.SysRoleResponseVO;
import com.seahorse.youliao.vo.response.SysUserInfoResponseVo;
import com.seahorse.youliao.vo.response.SysUserMenuResponseVO;
import com.wf.captcha.ArithmeticCaptcha;
import com.zengtengpeng.operation.RedissonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.controller
 * @ClassName: LoginController
 * @Description: 登录控制器
 * 登录控制器
 *
 * 除了使用登录认证过滤器拦截 /login Post请求之外，我们也可以不使用上面的过滤器，通过自定义登录接口实现，
 * 只要在登录接口手动触发登录流程并生产令牌即可。
 *
 * 其实 Spring Security 的登录认证过程只需调用 AuthenticationManager 的 authenticate(Authentication authentication) 方法，
 * 最终返回认证成功的 Authentication 实现类并存储到SpringContexHolder 上下文即可，这样后面授权的时候就可以从
 * SpringContexHolder 中获取登录认证信息，并根据其中的用户信息和权限信息决定是否进行授权。
 * @author:songqiang
 * @Date:2020-01-10 10:58
 **/
@Api(value = "LoginController", tags = "系统-spring security登录")
@RequestMapping("/auth")
@RestController
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysLoginLogService loginLogService;

    @Resource
    private RedissonObject redissonObject;

    /**
     * 登录接口
     * 注意：如果使用此登录控制器触发登录认证，需要禁用登录认证过滤器，即将 WebSecurityConfig 中的以下配置项注释即可，
     * http.addFilterBefore(new JwtLoginFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
     * 否则访问登录接口会被过滤拦截，
     * 执行不会再进入此登录接口，大家根据使用习惯二选一即可
     */
    @NoRepeatSubmit
    @PostMapping(value = "/login")
    public JwtAuthenticatioToken login(@RequestBody LoginRequestVO loginRequestVO, HttpServletRequest request) throws IOException {

        // 查询验证码
        String code = redissonObject.getValue(loginRequestVO.getUuid());
        // 清除验证码
        redissonObject.delete(loginRequestVO.getUuid());
        if (StringUtils.isBlank(code)) {
            logger.error("验证码不存在或已过期");
            throw new BusinessException("验证码不存在或已过期");
        }
        if (StringUtils.isBlank(loginRequestVO.getImgCode()) || !loginRequestVO.getImgCode().equalsIgnoreCase(code)) {
            logger.error("验证码错误");
            throw new BusinessException("验证码错误");
        }
        String username = loginRequestVO.getUsername();
        String password = loginRequestVO.getPassword();

        // 系统登录认证
        JwtAuthenticatioToken token = SecurityUtils.login(request, username, password, authenticationManager);

        //添加登录日志
        insertLoginLog(loginRequestVO, request);
        return token;
    }

    /**
     * 记录登录日志
     * @param loginRequestVO
     * @param request
     */
    private void insertLoginLog(LoginRequestVO loginRequestVO, HttpServletRequest request) {
        //记录登录日志
        SysLoginLogDTO loginLogDTO = new SysLoginLogDTO();
        loginLogDTO.setLoginName(loginRequestVO.getUsername());
        String ip = IpUtil.getIpAddr(request);
        loginLogDTO.setIp(ip);
        loginLogDTO.setLoginLocation(IpUtil.getCityInfo(ip));
        loginLogDTO.setBrowser(IpUtil.getBrowser(request));
        loginLogDTO.setOs(IpUtil.getOperatingSystem(request));
        loginLogDTO.setStatus(0);
        loginLogDTO.setLoginTime(new Date());
        loginLogService.insert(loginLogDTO);
    }


    /**
     * 验证码
     * @return
     */
    @NoRepeatSubmit
    @ApiOperation("获取验证码")
    @GetMapping(value = "/captcha")
    public ResponseEntity<Object> getCode(){
        // 算术类型 https://gitee.com/whvse/EasyCaptcha
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(111, 36);
        // 几位数运算，默认是两位
        captcha.setLen(2);
        // 获取运算的结果
        String result = captcha.text();
        String uuid = UUID.randomUUID().toString().replace("-","");
        // 保存到redis
        redissonObject.setValue(uuid,result,60*5*1000L);
        // 验证码信息
        Map<String,Object> imgResult = new HashMap<String,Object>(2){{
            put("img", captcha.toBase64());
            put("uuid", uuid);
        }};
        return ResponseEntity.ok("成功",imgResult);
    }

    /**
     * 登录用户信息
     * @return
     */
    @ApiOperation("登录用户信息")
    @GetMapping(value = "/user/info")
    public SysUserInfoResponseVo getUserInfo(){

        //查询用户信息
        SysUserDTO userDTO = new SysUserDTO();
        userDTO.setUserName(SecurityUtils.getUsername());
        SysUserDTO user = sysUserService.get(userDTO);
        SysUserInfoResponseVo responseVo = BeanUtil.convert(user,SysUserInfoResponseVo.class);

        //查询角色信息
        List<SysRoleDTO> roles = sysRoleService.getRolesByUserId(user.getId());
        List<SysRoleResponseVO> roleList = BeanUtil.convert(roles,SysRoleResponseVO.class);
        responseVo.setRoles(roleList);

        //查询角色权限信息
        List<String> permissions = sysMenuService.getPermissionsByUserId(user.getId());
        responseVo.setPermissions(permissions);

        return responseVo;
    }

    /**
     * 登录查询用户菜单
     * @return
     */
    @ApiOperation("登录查询用户菜单")
    @GetMapping(value = "/user/nav")
    public List<SysUserMenuResponseVO> getUserNav(){

        List<SysMenuDTO> list = sysUserService.getUserNav(SecurityUtils.getUsername());
        //处理数据
        return BeanUtil.convert(list,SysUserMenuResponseVO.class);
    }

}
