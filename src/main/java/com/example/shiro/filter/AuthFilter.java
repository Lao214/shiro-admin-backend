package com.example.shiro.filter;/*
 *@title AuthFilter
 *@description
 *@author echoes
 *@version 1.0
 *@create 2024/4/30 14:35
 */

import com.example.shiro.utils.AuthToken;
import com.example.shiro.utils.Result;
import com.google.gson.Gson;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 自定义认证授权过滤器  AuthFilter ,  正常来说对于常用部分需要封装，这样代码冗余度就不会那么大，也比较优雅，但是这只是个小demo，不想把它弄的那么复杂、不利于理解，索性就不封装了。
 *
 * 1、用户发起请求首先进入isAccessAllowed（）方法，拦截除了option请求 以外的请求， 浏览器机制就是：在发post、get请求之前，首先会发个option请求进行试探，所以要放行option请求通过，否则你的get、post请求永远进不来。
 *
 * 2、token不为空的情况下则生成属于自己的token，即创建一个类使其继承  UsernamePasswordToken此类
 *
 * 3、然后进入onAccessDenied（）方法去校验token是否存在，并执行executeLogin(request,response)方法
 * **/

public class AuthFilter extends AuthenticatingFilter {

    Gson gson=new Gson();

    //生成自定义token
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest= (HttpServletRequest) request;
        //从header中获取token
        String token=httpServletRequest.getHeader("token");
        return new AuthToken(token);
    }

    //所有请求全部拒绝访问
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //允许option请求通过
        if (((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true;
        }
        return false;
    }

    //拒绝访问的请求，onAccessDenied方法先获取 token，再调用executeLogin方法
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest= (HttpServletRequest) request;
        HttpServletResponse httpServletResponse= (HttpServletResponse) response;
        String token=httpServletRequest.getHeader("token");     //获取请求token

        //StringUtils.isBlank(String str)  判断str字符串是否为空或者长度是否为0
        if(org.apache.commons.lang3.StringUtils.isBlank(token)){
            httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpServletResponse.setHeader("Access-Control-Allow-Origin",httpServletRequest.getHeader("Origin") );
            httpServletResponse.setCharacterEncoding("UTF-8");
            Result msg= Result.error().msg("请先登录");
            httpServletResponse.getWriter().write(gson.toJson(msg));
            return false;
        }
        return executeLogin(request,response);
    }

    //token失效时调用
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest= (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpResponse.setCharacterEncoding("UTF-8");
        try {
            //处理登录失败的异常
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            Result msg= Result.error().msg("登录凭证已失效，请重新登录");
//            Msg msg=Msg.fail("登录凭证已失效，请重新登录");
            httpResponse.getWriter().write(gson.toJson(msg));
        } catch (IOException e1) {

        }
        return false;
    }
}
