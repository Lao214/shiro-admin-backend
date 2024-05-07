package com.example.shiro.config;/*
 *@title ShiroRealm
 *@description
 *@author echoes
 *@version 1.0
 *@create 2024/4/30 13:48
 */

import com.example.shiro.entity.BUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/** shiro的realm主要用来实现认证(AuthenticationInfo)和授权(AuthorizationInfo) **/
public class ShiroRealm  extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        return null;
    }
}
