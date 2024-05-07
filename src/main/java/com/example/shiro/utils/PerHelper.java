package com.example.shiro.utils;

/**
 * <p>
 * 权限树处理类
 * </p>
 *
 * @author 劳威锟
 * @since 2024-05-06
 */

import com.example.shiro.entity.BPer;

import java.util.ArrayList;
import java.util.List;

/** 该类用于递归权限，构成树形结构 **/

public class PerHelper {

    //构建树形结构
    public static List<BPer> bulidTree(List<BPer> bPerList) {
        //创建集合封装最终数据
        List<BPer> trees = new ArrayList<>();
        //遍历所有菜单集合
        for (BPer bPer:bPerList) {
            //找到递归入口，parentid=0
            if(bPer.getParentId().longValue()==0) {
                trees.add(findChildren(bPer,bPerList));
            }
        }
        return trees;
    }

    //从根节点进行递归查询，查询子节点
    // 判断 id =  parentid是否相同，如果相同是子节点，进行数据封装
    private static BPer findChildren(BPer bPer, List<BPer> treeNodes) {
        //数据初始化
        bPer.setChildren(new ArrayList<BPer>());
        //遍历递归查找
        for (BPer it:treeNodes) {
            //获取当前菜单id
//            String id = bPer.getId();
//            long cid = Long.parseLong(id);
            //获取所有菜单parentid
//            Long parentId = it.getParentId();
            //比对
            if(bPer.getId()==it.getParentId()) {
                if(bPer.getChildren()==null) {
                    bPer.setChildren(new ArrayList<>());
                }
                bPer.getChildren().add(findChildren(it,treeNodes));
            }
        }
        return bPer;
    }

}
