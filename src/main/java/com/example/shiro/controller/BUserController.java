package com.example.shiro.controller;


import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.shiro.entity.*;
import com.example.shiro.entity.Vo.UserQueryVo;
import com.example.shiro.entity.enumVo.RoleEnum;
import com.example.shiro.service.BPerService;
import com.example.shiro.service.BUserRoleService;
import com.example.shiro.service.BUserService;
import com.example.shiro.utils.PerHelper;
import com.example.shiro.utils.Result;
import com.example.shiro.utils.SHA256Utils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 劳威锟
 * @since 2023-02-18
 */
@RestController
@RequestMapping("/shiro/user")
public class BUserController {


    @Autowired
    BUserService bUserService;

    @Autowired
    BPerService bPerService;

    @Autowired
    BUserRoleService bUserRoleService;

    //通过java去操作redis缓存string类型的数据
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /** 需要权限为ROLE_USER才能访问 /** index **/
    @RequiresPermissions("ADMIN")
    @GetMapping("/index")
    public Result index(@RequestHeader String token){
        return Result.success().msg("index");
    }

    //需要权限ROLE_ADMIN才能访问hello
    @RequiresPermissions("SUPER_ADMIN")
    @GetMapping("/hello")
    public Result hello(@RequestHeader String token){
        return Result.success().msg("hello");
    }


    //登录接口
    @PostMapping("/login")
    public Result login(@RequestBody UserValidate userValidate) {
        QueryWrapper<BUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userValidate.getUsername());
        BUser user = bUserService.getOne(queryWrapper);
//        String realPassword = SHA256Utils.hashPassword(userValidate.getPassword(), user.getPasswordSalt());
        if (user == null) {
            return Result.error().msg("账号错误");
        } else if (!SHA256Utils.hashPassword(userValidate.getPassword(), user.getPasswordSalt()).equals(user.getPassword())) {
            return Result.error().msg("密码错误");
        } else {
            //通过UUID生成token字符串,并将其以string类型的数据保存在redis缓存中，key为token，value为username
            String token= UUID.randomUUID().toString().replaceAll("-","");
            stringRedisTemplate.opsForValue().set(token,userValidate.getUsername(),43200, TimeUnit.SECONDS);
            List<BPer> bPerList = bPerService.findPerListUserId(user.getId(),null);
            String per_str_list =  "";
            if(!bPerList.isEmpty()) {
                for (BPer bPer: bPerList) {
                    per_str_list = per_str_list + bPer.getPerCode() + ",";
                }
            }
            // 如果不希望最后一个字符是逗号，可以这样处理
            if (!per_str_list.isEmpty()) {
                per_str_list = per_str_list.substring(0, per_str_list.length() - 1);
            }
            stringRedisTemplate.opsForValue().set("shiro-" + userValidate.getUsername() ,per_str_list,43200, TimeUnit.SECONDS);
            return Result.success().msg("登录成功").data("token",token);
        }
    }

    //注销接口
    @PostMapping("/logout")
    public Result logout(@RequestHeader("token")String token){
        //删除redis缓存中的token
        stringRedisTemplate.delete(token);
        return Result.success().msg("注销成功");
    }


    /**
     * @description:  邮箱登录
     * @param:
     * @return:
     * @author 劳威锟
     * @date: 2023/7/31 2:31 PM
     */
    @GetMapping("info")
    public Result info(HttpServletRequest request) {
        // 获取请求头token字符串
        String token = request.getHeader("token");
        if (token == null) {
            return Result.error().code(201).msg("请重新登录");
        }
        String username = stringRedisTemplate.opsForValue().get(token);

        // 根据token获取ID
        try {
            QueryWrapper<BUser> saUserQueryWrapper =new QueryWrapper<>();
            saUserQueryWrapper.eq("username",username);
            BUser one = bUserService.getOne(saUserQueryWrapper);
            //根据用户名称获取用户信息（基本信息 和 菜单权限 和 按钮权限数据）
            List<BPer> perListUserId = bPerService.findPerListUserId(one.getId(), 2);
            List<BPer> bPerList = PerHelper.bulidTree(perListUserId);
            one.setMenus(bPerList);
            return Result.success().data("one",one);
        }catch (Exception e) {
            return Result.error().code(201).msg("请重新登录");
        }
    }


    @GetMapping("getPageList/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, UserQueryVo userQueryVo) {
        //创建page对象
        Page<BUser> pageParam = new Page<>(page,limit);
        //调用service方法
        IPage<BUser> pageModel = bUserService.selectPage(pageParam,userQueryVo);
        return Result.success().data("data",pageModel);
    }

    @GetMapping("getUser/{id}")
    public Result getUser(@PathVariable String id) {
        BUser user = bUserService.getById(id);
        return Result.success().data("data",user);
    }

    @PostMapping("update")
    public Result update(@RequestBody BUser user) {
        boolean is_Success = bUserService.updateById(user);
        if(is_Success) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @PostMapping("/addUser")
    @Transactional(rollbackFor = Exception.class)
    public Result addUser(@RequestBody BUser user){
        String salt = SHA256Utils.generateSalt();
        user.setPasswordSalt(salt);
        user.setPassword(SHA256Utils.hashPassword(user.getPassword(), salt));
        boolean save = bUserService.save(user);
        if(save) {
            BUserRole bUserRole = new BUserRole();
            bUserRole.setUserId(user.getId());
            bUserRole.setRoleId(RoleEnum.GENERAL_USER.getRoleId());
            bUserRoleService.save(bUserRole);
            return Result.success().msg("添加成功");
        } else  {
            return Result.error().msg("添加成功");
        }
    }


    @DeleteMapping("remove/{id}")
    @Transactional
    public Result remove(@PathVariable String id) {
        boolean is_Success = bUserService.removeById(id);
        if(is_Success) {
            QueryWrapper<BUserRole> bUserRoleQueryWrapper = new QueryWrapper<>();
            bUserRoleQueryWrapper.eq("user_id",id);
            bUserRoleService.remove(bUserRoleQueryWrapper);
            return Result.success();
        } else {
            return Result.error();
        }
    }

}

