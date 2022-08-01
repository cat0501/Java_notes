package com.zjl.litemall.db.service;

import com.zjl.litemall.db.dao.LitemallRoleMapper;
import com.zjl.litemall.db.domain.LitemallRole;
import com.zjl.litemall.db.example.LitemallRoleExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author cat
 * @description
 * @date 2022/4/5 上午11:09
 */
@Service
public class RoleService {

    @Autowired
    LitemallRoleMapper rolemapper;

    public Set<String> queryByIds(Integer[] roleIds) {
        Set<String> role = new HashSet<>();
        if (roleIds.length == 0){
            return role;
        }

        LitemallRoleExample roleExample = new LitemallRoleExample();
        roleExample.or().andIdIn(Arrays.asList(roleIds)).andDeletedEqualTo(false).andEnabledEqualTo(true);
        List<LitemallRole> roleList = rolemapper.selectByExample(roleExample);
        for (LitemallRole litemallRole : roleList) {
            role.add(litemallRole.getName());
        }
        return role;
    }

}
