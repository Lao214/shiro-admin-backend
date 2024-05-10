package com.example.shiro.service;

import com.example.shiro.entity.BPer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shiro.entity.Vo.AssginMenuVo;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author 劳威锟
 * @since 2024-05-06
 */
public interface BPerService extends IService<BPer> {


    List<BPer> findPerListUserId(Long userId, Integer perType);

    List<BPer> findMenuByRoleId(Long roleId);

    void doAssign(AssginMenuVo assginMenuVo);

    List<BPer> findNodes();

    void removePerById(String id);
}
