package com.example.shiro.utils;/*
 *@title AuthRealm
 *@description
 *@author echoes
 *@version 1.0
 *@create 2024/4/30 14:40
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.shiro.entity.BUser;
import com.example.shiro.service.BUserService;
import com.example.shiro.service.impl.BUserServiceImpl;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * AuthRealm.java是自定义的realm，继承AuthorizingRealm,实现doGetAuthenticationInfo(AuthenticationToken token)认证
 * 和 doGetAuthorizationInfo(PrincipalCollection principals）授权
 */

public class AuthRealm extends AuthorizingRealm {

    @Autowired
    BUserService buserService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取前端传来的token
        String accessToken = (String) token.getPrincipal();

        //redis缓存中这样存值， key为token，value为username
        //根据token去缓存里查找用户名
        String username = stringRedisTemplate.opsForValue().get(accessToken);
        String perList = stringRedisTemplate.opsForValue().get("shiro-" + username);
        if(username==null) {
            //查找的用户名为空，即为token失效
            throw new IncorrectCredentialsException("token失效，请重新登录");
        }

        QueryWrapper<BUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        BUser user = buserService.getOne(queryWrapper);
        if(user==null) {
            throw new UnknownAccountException("用户不存在!");
        }

        if(!perList.isEmpty()) {
            String[] split = perList.split(",");
            user.setPerList(split);
        }

        //此方法需要返回一个AuthenticationInfo类型的数据
        // 因此返回一个它的实现类SimpleAuthenticationInfo,将user以及获取到的token传入它可以实现自动认证
        SimpleAuthenticationInfo simpleAuthenticationInfo=new SimpleAuthenticationInfo(user,accessToken,"");
        return simpleAuthenticationInfo;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 从认证那里获取到用户对象User
        BUser user = (BUser) principals.getPrimaryPrincipal();

        // 此方法需要一个AuthorizationInfo类型的返回值，因此返回一个它的实现类SimpleAuthorizationInfo
        // 通过SimpleAuthorizationInfo里的addStringPermission()设置用户的权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//        simpleAuthorizationInfo.addStringPermission(user.getUserRole());
        String[] perList = user.getPerList();
        if(perList!=null) {
            for (String per : perList) {
                simpleAuthorizationInfo.addStringPermission(per);
            }
        }
//        simpleAuthorizationInfo.setStringPermissions(user.getPerList());
        return simpleAuthorizationInfo;
    }
}
