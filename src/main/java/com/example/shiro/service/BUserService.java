package com.example.shiro.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.shiro.entity.BUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shiro.entity.Vo.UserQueryVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 劳威锟
 * @since 2023-02-18
 */
public interface BUserService extends IService<BUser> {

    IPage<BUser> selectPage(Page<BUser> pageParam, UserQueryVo userQueryVo);
}
