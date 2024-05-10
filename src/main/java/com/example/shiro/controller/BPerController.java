package com.example.shiro.controller;


import com.example.shiro.entity.BPer;
import com.example.shiro.entity.Vo.AssginMenuVo;
import com.example.shiro.service.BPerService;
import com.example.shiro.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author 劳威锟
 * @since 2024-05-06
 */
@RestController
@RequestMapping("/shiro/per")
public class BPerController {

    @Autowired
    private BPerService bPerService;

    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginMenuVo assginMenuVo) {
        bPerService.doAssign(assginMenuVo);
        return Result.success();
    }

    @GetMapping("/toAssign/{roleId}")
    public Result toAssign(@PathVariable Long roleId) {
        List<BPer> list = bPerService.findMenuByRoleId(roleId);
        return Result.success().data("data",list);
    }

    @GetMapping("findNodes")
    public Result findNodes() {
        List<BPer> list = bPerService.findNodes();
        return Result.success().data("data",list);
    }

    @PostMapping("save")
    public Result save(@RequestBody BPer sysMenu) {
        bPerService.save(sysMenu);
        return Result.success();
    }

    @GetMapping("findNode/{id}")
    public Result findNode(@PathVariable String id) {
        BPer sysMenu = bPerService.getById(id);
        return  Result.success().data("data",sysMenu);
    }

    @PostMapping("update")
    public Result update(@RequestBody BPer sysMenu) {
        bPerService.updateById(sysMenu);
        return Result.success();
    }

    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable String id) {
        bPerService.removePerById(id);
        return Result.success();
    }

}

