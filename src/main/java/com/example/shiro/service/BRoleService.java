package com.example.shiro.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.shiro.entity.BRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shiro.entity.Vo.AssginRoleVo;
import com.example.shiro.entity.Vo.UserQueryVo;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 劳威锟
 * @since 2024-05-06
 */
public interface BRoleService extends IService<BRole> {

    Map<String, Object> getRolesByUserId(String userId);

    void doAssign(AssginRoleVo assginRoleVo);

    IPage<BRole> selectPage(Page<BRole> pageParam, UserQueryVo userQueryVo);
}
