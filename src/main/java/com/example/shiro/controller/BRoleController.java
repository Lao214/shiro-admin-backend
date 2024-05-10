package com.example.shiro.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.shiro.entity.BRole;
import com.example.shiro.entity.BUser;
import com.example.shiro.entity.Vo.AssginRoleVo;
import com.example.shiro.entity.Vo.UserQueryVo;
import com.example.shiro.service.BRoleService;
import com.example.shiro.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 劳威锟
 * @since 2024-05-06
 */
@RestController
@RequestMapping("/shiro/role")
public class BRoleController {

    @Autowired
    private BRoleService bRoleService;

    @GetMapping("toAssign/{userId}")
    public Result toAssign(@PathVariable String userId) {
        Map<String,Object> roleMap = bRoleService.getRolesByUserId(userId);
        return Result.success().data("data",roleMap);
    }

    @PostMapping("doAssign")
    public Result doAssign(@RequestBody AssginRoleVo assginRoleVo) {
        bRoleService.doAssign(assginRoleVo);
        return Result.success();
    }

    @GetMapping("getPageList/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, UserQueryVo userQueryVo) {
        //创建page对象
        Page<BRole> pageParam = new Page<>(page,limit);
        //调用service方法
        IPage<BRole> pageModel = bRoleService.selectPage(pageParam,userQueryVo);
        return Result.success().data("data",pageModel);
    }

    /**
     * 添加角色
     * @param bRole
     * @return
     * @Author 劳威锟
     */
    @PostMapping("save")
    public Result saveRole(@RequestBody BRole bRole) {
        boolean isSuccess = bRoleService.save(bRole);
        if(isSuccess) {
            return Result.success();
        } else {
            return Result.error();
        }
    }


    /**
     * 根据id查询角色信息
     * @param id
     * @return
     */
    @PostMapping("findRoleById/{id}")
    public Result findRoleById(@PathVariable Long id) {
        BRole sysRole = bRoleService.getById(id);
        return Result.success().data("data",sysRole);
    }

    /**
     * @description
     * @author echoes
     * @param[1] null
     * @throws
     * @return
     * @time 2024/5/9 19:12
     */
    @PostMapping("update")
    public Result updateRole(@RequestBody BRole bRole) {
        boolean isSuccess = bRoleService.updateById(bRole);
        if(isSuccess) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> ids) {
        bRoleService.removeByIds(ids);
        return Result.success();
    }

    @DeleteMapping("remove/{id}")
    public Result removeRole(@PathVariable Long id) {
        //调用方法删除
        boolean isSuccess = bRoleService.removeById(id);
        if(isSuccess) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

}

