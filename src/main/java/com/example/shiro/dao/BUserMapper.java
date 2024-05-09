package com.example.shiro.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.shiro.entity.BUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shiro.entity.Vo.UserQueryVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 劳威锟
 * @since 2023-02-18
 */
public interface BUserMapper extends BaseMapper<BUser> {

    IPage<BUser> selectPage(Page<BUser> pageParam,@Param("vo")  UserQueryVo sysUserQueryVo);
}
