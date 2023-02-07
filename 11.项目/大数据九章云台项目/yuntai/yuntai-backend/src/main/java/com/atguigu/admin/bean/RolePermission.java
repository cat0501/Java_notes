package com.atguigu.admin.bean;

import java.util.List;

public class RolePermission {
    private Long roleId;
    private String permissionIds;

    public RolePermission() {
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(String permissionIds) {
        this.permissionIds = permissionIds;
    }
}
