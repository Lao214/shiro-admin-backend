package com.example.shiro.controller;


import com.example.shiro.entity.BRole;
import com.example.shiro.entity.Vo.AssginRoleVo;
import com.example.shiro.service.BRoleService;
import com.example.shiro.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}

