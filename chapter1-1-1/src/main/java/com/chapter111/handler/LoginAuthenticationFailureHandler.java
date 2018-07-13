package com.chapter111.handler;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 登录失败跳转的类
 * 验证失败页面
 * @author yincl
 *
 */
public class LoginAuthenticationFailureHandler implements AuthenticationFailureHandler {

    //@Autowired
    private UserService userService;
    private LoginRegisterService loginRegisterService;
    private SysLogService sysLogService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    public void setLoginRegisterService(LoginRegisterService loginRegisterService) {
        this.loginRegisterService = loginRegisterService;
    }
    public void setSysLogService(SysLogService sysLogService) {
        this.sysLogService = sysLogService;
    }
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response, AuthenticationException authentication)
            throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        //增加操作日志
        sysLogService.createLog(BusinessType.loginOp.toString(), EventType.platform_login.toString(),"登录失败","", Loglevel.error.toString(),ip,source);
        out.println("{\"code\":\"0\",\"url\":\"\"}");
    }

}