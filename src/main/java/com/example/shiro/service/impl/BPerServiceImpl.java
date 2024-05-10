package com.example.shiro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.shiro.dao.BRolePerMapper;
import com.example.shiro.entity.BPer;
import com.example.shiro.dao.BPerMapper;
import com.example.shiro.entity.BRolePer;
import com.example.shiro.entity.Vo.AssginMenuVo;
import com.example.shiro.service.BPerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shiro.utils.Exception.EchoesException;
import com.example.shiro.utils.PerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author 劳威锟
 * @since 2024-05-06
 */
@Service
public class BPerServiceImpl extends ServiceImpl<BPerMapper, BPer> implements BPerService {

    @Autowired
    private BRolePerMapper bRolePerMapper;

    @Override
    public List<BPer> findPerListUserId(Long userId, Integer perType) {
        return baseMapper.findPerListUserId(userId,perType);
    }

    @Override
    public List<BPer> findMenuByRoleId(Long roleId) {
        //获取所有菜单 status=1
        QueryWrapper<BPer> wrapperMenu = new QueryWrapper<>();
        wrapperMenu.eq("per_type",2);
        List<BPer> menuList = baseMapper.selectList(wrapperMenu);

        //根据角色id查询 角色分配过的菜单列表
        QueryWrapper<BRolePer> wrapperRoleMenu = new QueryWrapper<>();
        wrapperRoleMenu.eq("role_id",roleId);
        List<BRolePer> roleMenus = bRolePerMapper.selectList(wrapperRoleMenu);

        //从第二步查询列表中，获取角色分配所有菜单id
        List<Long> roleMenuIds = new ArrayList<>();
        for (BRolePer sysRoleMenu:roleMenus) {
            Long menuId = sysRoleMenu.getPerId();
            roleMenuIds.add(menuId);
        }

        //数据处理：isSelect 如果菜单选中 true，否则false
        // 拿着分配菜单id 和 所有菜单比对，有相同的，让isSelect值true
        for (BPer bPer:menuList) {
            if(roleMenuIds.contains(bPer.getId())) {
                bPer.setSelect(true);
            } else {
                bPer.setSelect(false);
            }
        }

        //转换成树形结构为了最终显示 PerHelper方法实现
        List<BPer> bPers = PerHelper.bulidTree(menuList);
        return bPers;
    }

    //给角色分配菜单权限
    @Override
    public void doAssign(AssginMenuVo assginMenuVo) {
        //根据角色id删除菜单权限
        QueryWrapper<BRolePer> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",assginMenuVo.getRoleId());
        bRolePerMapper.delete(wrapper);

        //遍历菜单id列表，一个一个进行添加
        List<Long> menuIdList = assginMenuVo.getMenuIdList();
        for (Long menuId:menuIdList) {
            BRolePer sysRoleMenu = new BRolePer();
            sysRoleMenu.setPerId(menuId);
            sysRoleMenu.setRoleId(assginMenuVo.getRoleId());
            bRolePerMapper.insert(sysRoleMenu);
        }
    }


    @Override
    public List<BPer> findNodes() {
        //获取所有菜单
        List<BPer> sysMenuList = baseMapper.selectList(null);
        //所有菜单数据转换要求数据格式
        List<BPer> resultList = PerHelper.bulidTree(sysMenuList);
        return resultList;
    }


    @Override
    public void removePerById(String id) {
        //查询当前删除菜单下面是否子菜单
        //根据id = parentid
        QueryWrapper<BPer> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0) {//有子菜单
            throw new EchoesException(201,"请先删除子菜单");
        }
        //调用删除
        baseMapper.deleteById(id);
    }
}
