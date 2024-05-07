package com.example.shiro.service.impl;

import com.example.shiro.entity.BPer;
import com.example.shiro.dao.BPerMapper;
import com.example.shiro.service.BPerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    @Override
    public List<BPer> findPerListUserId(Long userId, Integer perType) {
        return baseMapper.findPerListUserId(userId,perType);
    }
}
