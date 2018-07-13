package com.chapter111.interceptor;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * @description 一个自定义的filter，
 *     必须包含authenticationManager,accessDecisionManager,securityMetadataSource三个属性，
我们的所有控制将在这三个类中实现
 */
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor
        implements Filter {

    private Logger logger = Logger.getLogger(MyFilterSecurityInterceptor.class);
    //注入userService服务
    private UserService userService;
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private FilterInvocationSecurityMetadataSource fisMetadataSource;
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return fisMetadataSource;
    }

    public void destroy() {}

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse) response;
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        Collection<ConfigAttribute> attributes = fisMetadataSource.getAttributes(fi);
        String url1 = fi.getFullRequestUrl();
        String token1 = request.getParameter("token");
        if(url1.indexOf("/system") != -1 || url1.indexOf(".jsp") != -1){
            this.publicSuccess(fi,(HttpServletRequest)request,(HttpServletResponse)response);
        }
        if(null != token1 && !"".equals(token1)) {
            TenantUser tenantUser = null;
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            try {
                tenantUser = (TenantUser) auth.getPrincipal();
            } catch (Exception e) {
                publicError((HttpServletRequest) request,(HttpServletResponse) response, 1);
                return;
            }
            if(null != tenantUser){
                //获取当前用户所在企业的租户id
                String tenantId = tenantUser.getTenantId();
                if(StringUtils.isBlank(tenantId)){
                    publicError((HttpServletRequest) request,(HttpServletResponse) response, 1);
                    return;
                }
                TenantContext.setTenantId(tenantId);
                //获取当前用户的主键id
                String userId = tenantUser.getUserId();
                if(StringUtils.isBlank(userId)){
                    publicError((HttpServletRequest) request,(HttpServletResponse) response, 1);
                    return;
                }
                //获取用户信息
                SysUser sysUser = userService.selectUserByUserId(userId);
                if(null == sysUser){
                    //publicError((HttpServletResponse) response,1);
                    publicError((HttpServletRequest) request,(HttpServletResponse) response, 1);
                    return;
                }
                //获取用户状态
                Integer userStatus = sysUser.getStatus();
                if(null != userStatus && userStatus == 89){
                    logger.info("当前用户在当前企业下是离职状态！！！");
                    if(!isAjaxRequest) {
                        goToUrl(fi, "/quitPage.jsp");
                    }else{
                        goToJsonUrl(fi,"/system/gotoQuitPage","当前用户在当前企业下是离职状态！！！","8");
                    }
                    return;
                }else if(null != userStatus && userStatus == 3){
                    logger.info("当前用户在当前企业下是禁用状态！！！");
                    if(!isAjaxRequest) {
                        goToUrl(fi, "/disablePage.jsp");
                    }else{
                        goToJsonUrl(fi,"/system/gotoDisablePage","当前用户在当前企业下是禁用状态！！！","9");
                    }
                    return;
                }

                String tokenn = RedisUtil.get("xxxx." + tenantUser.getTenantId() +"."+tenantUser.getUserId());
                if (!token1.equals(tokenn)) {
                    HttpServletRequest req1 = (HttpServletRequest)request;
                    HttpServletResponse res1 = (HttpServletResponse) response;
                    publicError(req1,res1, 1);
                    return;
                } else {
                    RedisUtil.expire("xxxx." + tenantUser.getTenantId() + "."+tenantUser.getUserId(), 900);
                    if (tenantUser != null) {
                        TenantContext.setTenantId(tenantUser.getTenantId());
                        TenantUserVo tenantUserVo = new TenantUserVo();
                        tenantUserVo.setUserId(userId);
                        tenantUserVo.setUserName(tenantUser.getUserName());
                        tenantUserVo.setTenantId(tenantUser.getTenantId());
                        tenantUserVo.setEmail(tenantUser.getEmail());
                        tenantUserVo.setIp(tenantUser.getIp());
                        tenantUserVo.setDevice(tenantUser.getDevice());
                        TenantContext.setUserInfo(tenantUserVo);
                    }
                    this.publicSuccess(fi, (HttpServletRequest) request, (HttpServletResponse) response);
                }
            }
        }else{
            goToUrl(fi, "/access.jsp");
            return;
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

    public static void goToUrl( Object obj,String url ){
        FilterInvocation fi = (FilterInvocation) obj;
        HttpServletRequest request = fi.getRequest();
        HttpServletResponse response = fi.getResponse();
        response.setCharacterEncoding("UTF-8");
        String responseUrl = request.getContextPath() + url;
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println("<html>");
        out.println("<script type=\"text/javascript\">");
        out.println("window.open ('" + responseUrl + "','_top');");
        out.println("</script>");
        out.println("</html>");
    }

    public static void goToJsonUrl( Object obj,String url,String msg,String code){
        FilterInvocation fi = (FilterInvocation) obj;
        HttpServletResponse response = fi.getResponse();
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
            out.println("{\"code\":\"0\",\"msg\":\"操作失败！\"}");
        }
        out.println("{\"code\":\""+code+"\",\"msg\":\""+msg+"\",\"url\":\"" + url + "\"}");
    }

    public static void goToJsonUrl( HttpServletRequest request,HttpServletResponse response,String url,String msg,String code){
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
            out.println("{\"code\":\"0\",\"msg\":\"操作失败！\"}");
        }
        out.println("{\"code\":\""+code+"\",\"msg\":\""+msg+"\",\"url\":\"" + url + "\"}");
    }

    public void setFisMetadataSource(
            FilterInvocationSecurityMetadataSource fisMetadataSource) {
        this.fisMetadataSource = fisMetadataSource;
    }

    public void publicError(HttpServletRequest request,HttpServletResponse response,Integer status) throws IOException {
        boolean isAjaxRequest = false;
        if(!StringUtils.isBlank(request.getHeader("x-requested-with")) && request.getHeader("x-requested-with").equals("XMLHttpRequest")){
            isAjaxRequest = true;
        }
        logger.error("出现异常:");
        if(!isAjaxRequest) {
            String url = "/access.jsp";
            if(null != status && status == 2){
                logger.info("第二处error.jsp出现处！！！");
                url = "/error.jsp";
            }
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<script type=\"text/javascript\">");
            out.println("window.open ('" + url + "','_top');");
            out.println("</script>");
            out.println("</html>");
        }else {
            if(null != status && status == 2){
                goToJsonUrl(request,response,"/system/gotoErrorPage","请求失败！！！","10");
            }
            goToJsonUrl(request,response,"/system/loginpage","权限不足！！！","10");
        }
    }
    public void publicSuccess(FilterInvocation fi,HttpServletRequest request,HttpServletResponse response) throws IOException {
        boolean isAjaxRequest = false;
        if(!StringUtils.isBlank(request.getHeader("x-requested-with")) && request.getHeader("x-requested-with").equals("XMLHttpRequest")){
            isAjaxRequest = true;
        }
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } catch (Exception e) {
            logger.error("出现异常:" + e.getMessage());
            if(!isAjaxRequest) {
                String url = "";
                if ("Access is denied".equals(e.getMessage())) {
                    url = request.getContextPath() + "/access.jsp";
                } else {
                    logger.info("第三处error.jsp出现处！！！");
                    url = request.getContextPath() + "/error.jsp";
                }
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                out.println("<html>");
                out.println("<script type=\"text/javascript\">");
                out.println("window.open ('" + url + "','_top');");
                out.println("</script>");
                out.println("</html>");
                return;
            }else {
                response.setHeader("Content-type", "application/json;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                if ("Access is denied".equals(e.getMessage())) {
                    goToJsonUrl(request,response,"/system/loginpage","权限不足！！！","10");
                } else {
                    logger.info("第三处error.jsp出现处！！！");
                    goToJsonUrl(request,response,"/system/gotoErrorPage","请求失败！！！","10");
                }
            }
        }finally{
            super.afterInvocation(token,null);
        }
    }
}