package com.example.shiro.dao;

import com.example.shiro.entity.BPer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author 劳威锟
 * @since 2024-05-06
 */
public interface BPerMapper extends BaseMapper<BPer> {

    List<BPer> findPerListUserId(@Param("userId") Long userId,@Param("perType") Integer perType);
}
