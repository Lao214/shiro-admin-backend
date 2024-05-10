package com.example.shiro.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.shiro.entity.BRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shiro.entity.Vo.UserQueryVo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 劳威锟
 * @since 2024-05-06
 */
public interface BRoleMapper extends BaseMapper<BRole> {

    IPage<BRole> selectPage(Page<BRole> pageParam,@Param("vo") UserQueryVo userQueryVo);
}
