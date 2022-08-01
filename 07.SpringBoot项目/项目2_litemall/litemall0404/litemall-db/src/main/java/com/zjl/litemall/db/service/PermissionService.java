package com.zjl.litemall.db.service;

import com.zjl.litemall.db.dao.LitemallPermissionMapper;
import com.zjl.litemall.db.domain.LitemallPermission;
import com.zjl.litemall.db.example.LitemallPermissionExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author cat
 * @description
 * @date 2022/4/5 上午11:19
 */
@Service
public class PermissionService {

    @Autowired
    LitemallPermissionMapper permissionMapper;

    public Set<String> queryByRoleIds(Integer[] roleIds) {
        Set<String> permissions = new HashSet<>();
        if (roleIds.length == 0){
            return permissions;
        }

        LitemallPermissionExample permissionExample = new LitemallPermissionExample();
        permissionExample.or().andRoleIdIn(Arrays.asList(roleIds)).andDeletedNotEqualTo(true);
        List<LitemallPermission> permissionList = permissionMapper.selectByExample(permissionExample);

        for (LitemallPermission permission : permissionList) {
            permissions.add(permission.getPermission());
        }
        return permissions;
    }

}
