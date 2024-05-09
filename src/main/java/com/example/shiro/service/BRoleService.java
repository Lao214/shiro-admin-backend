package com.example.shiro.service;

import com.example.shiro.entity.BRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shiro.entity.Vo.AssginRoleVo;

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
}
