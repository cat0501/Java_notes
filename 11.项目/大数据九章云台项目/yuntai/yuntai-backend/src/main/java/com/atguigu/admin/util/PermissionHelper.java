package com.atguigu.admin.util;

import com.atguigu.admin.bean.Permission;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 根据权限数据构建菜单数据
 */
public class PermissionHelper {
    /**
     * 使用递归方法建菜单
     */
    public static ArrayList<Permission> build(List<Permission> treeNodes) {
        var trees = new ArrayList<Permission>();
        for (var treeNode : treeNodes) {
            if (treeNode.getParentId() == 0) {
                treeNode.setLevel(1);
                trees.add(findChildren(treeNode, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     */
    public static Permission findChildren(Permission treeNode, List<Permission> treeNodes) {
        treeNode.setChildren(new ArrayList<>());

        for (var it : treeNodes) {
            if (Objects.equals(treeNode.getId(), it.getParentId())) {
                it.setLevel(treeNode.getLevel() + 1);
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.getChildren().add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }
}
