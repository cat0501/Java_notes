package com.atguigu.admin.service;

import com.atguigu.admin.bean.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleService {
    /**
     * 获取所有角色
     */
    public static List<Role> getAllRoles() {
        try {
            Class.forName(DATABASE.DRIVER);
            var connection = DriverManager.getConnection(DATABASE.URL, DATABASE.USERNAME, DATABASE.PASSWORD);
            var selectStatement = connection.prepareStatement(
                    "SELECT id, role_name FROM role"
            );
            var roles = new ArrayList<Role>();
            var resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                var role = new Role();
                role.setId(resultSet.getLong("id"));
                role.setRoleName(resultSet.getString("role_name"));
                roles.add(role);
            }
            selectStatement.close();
            connection.close();
            return roles;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 根据用户ID获取对应的角色名称
     */
    public static String getUserRoleName(Long userId) {
        try {
            Class.forName(DATABASE.DRIVER);
            var connection = DriverManager.getConnection(DATABASE.URL, DATABASE.USERNAME, DATABASE.PASSWORD);
            var selectStatement = connection.prepareStatement(
                    "SELECT role.role_name AS roleName FROM user_role" +
                            "  INNER JOIN role ON user_role.role_id = role.id" +
                            "  WHERE user_role.user_id = ?"
            );
            selectStatement.setLong(1, userId);
            var resultSet = selectStatement.executeQuery();
            var roleName = "";
            if (resultSet.next()) {
                roleName = resultSet.getString("roleName");
            }

            selectStatement.close();
            connection.close();
            return roleName;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 删除角色
     */
    public static void deleteRole(Long roleId) {
        try {
            Class.forName(DATABASE.DRIVER);
            var connection = DriverManager.getConnection(DATABASE.URL, DATABASE.USERNAME, DATABASE.PASSWORD);

            var deleteRoleStatement = connection.prepareStatement(
                    "DELETE FROM role WHERE id = ?"
            );
            deleteRoleStatement.setLong(1, roleId);
            deleteRoleStatement.execute();

            var deleteRolePermissionStatement = connection.prepareStatement(
                    "DELETE FROM role_permission WHERE role_id = ?"
            );
            deleteRolePermissionStatement.setLong(1, roleId);
            deleteRolePermissionStatement.execute();

            var deleteUserRoleStatement = connection.prepareStatement(
                    "DELETE FROM user_role WHERE role_id = ?"
            );
            deleteUserRoleStatement.setLong(1, roleId);
            deleteUserRoleStatement.execute();

            deleteRoleStatement.close();
            deleteRolePermissionStatement.close();
            deleteUserRoleStatement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加新角色
     */
    public static void addRole(String roleName) {
        try {
            Class.forName(DATABASE.DRIVER);
            var connection = DriverManager.getConnection(DATABASE.URL, DATABASE.USERNAME, DATABASE.PASSWORD);
            var insertStatement = connection.prepareStatement(
                    "INSERT INTO role (role_name) VALUES (?)"
            );
            insertStatement.setString(1, roleName);
            insertStatement.execute();

            insertStatement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
