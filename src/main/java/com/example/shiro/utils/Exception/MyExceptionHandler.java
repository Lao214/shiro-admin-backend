package com.example.shiro.utils.Exception;/*
 *@title MyExceptionHandler
 *@description
 *@author echoes
 *@version 1.0
 *@create 2024/4/30 14:55
 */

import com.example.shiro.utils.Result;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//@ControllerAdvice可以实现全局异常处理，可以简单理解为增强了的controller
@ControllerAdvice
public class MyExceptionHandler {

    //捕获AuthorizationException的异常
    @ExceptionHandler(value = AuthorizationException.class)
    @ResponseBody
    public Result handleException(AuthorizationException e) {
        Result msg=Result.denyAccess().msg("权限不足呀！！！！！");
        return msg;
    }
}