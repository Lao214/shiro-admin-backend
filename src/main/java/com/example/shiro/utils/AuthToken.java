package com.example.shiro.utils;/*
 *@title AuthToken
 *@description
 *@author echoes
 *@version 1.0
 *@create 2024/4/30 14:38
 */

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 自定义 AuthToken.java 去继承 UsernamePasswordToken这个类，因为源码里的subject.login(token)需要传入一个自定义的token参数
 */

public class AuthToken extends UsernamePasswordToken {
    String token;

    public AuthToken(String token){
        this.token=token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}