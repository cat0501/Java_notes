package com.atguigu.admin.service;

import com.atguigu.admin.bean.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    /**
     * 获取所有用户信息
     */
    public static List<User> getAllUsers() {
        try {
            Class.forName(DATABASE.DRIVER);
            var connection = DriverManager.getConnection(DATABASE.URL, DATABASE.USERNAME, DATABASE.PASSWORD);
            var selectStatement = connection.prepareStatement(
                    "SELECT id, username, password FROM user"
            );
            var resultSet = selectStatement.executeQuery();
            var users = new ArrayList<User>();
            while (resultSet.next()) {
                var user = new User();
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setRoleName(RoleService.getUserRoleName(resultSet.getLong("id")));
                users.add(user);
            }

            selectStatement.close();
            connection.close();
            return users;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    /**
     * 根据用户名获取用户信息
     */
    public static User getUserByName(String username) {
        try {
            Class.forName(DATABASE.DRIVER);
            var connection = DriverManager.getConnection(DATABASE.URL, DATABASE.USERNAME, DATABASE.PASSWORD);
            var selectStatement = connection.prepareStatement(
                    "SELECT id, username, password FROM user WHERE username = ?"
            );
            selectStatement.setString(1, username);
            var resultSet = selectStatement.executeQuery();
            var user = new User();
            if (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setRoleName(RoleService.getUserRoleName(resultSet.getLong("id")));
            }

            selectStatement.close();
            connection.close();
            return user;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据用户ID获取用户信息
     */
    public static User getUserById(Long userId) {
        try {
            Class.forName(DATABASE.DRIVER);
            var connection = DriverManager.getConnection(DATABASE.URL, DATABASE.USERNAME, DATABASE.PASSWORD);
            PreparedStatement selectStatement = connection.prepareStatement(
                    "SELECT id, username, password FROM user WHERE id = ?"
            );
            selectStatement.setLong(1, userId);
            var resultSet = selectStatement.executeQuery();
            var user = new User();
            if (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setRoleName(RoleService.getUserRoleName(resultSet.getLong("id")));
            }

            selectStatement.close();
            connection.close();
            return user;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据用户ID删除用户及用户对应的角色信息
     */
    public static void deleteUserById(Long userId) {
        try {
            Class.forName(DATABASE.DRIVER);
            var connection = DriverManager.getConnection(
                    DATABASE.URL,
                    DATABASE.USERNAME,
                    DATABASE.PASSWORD
            );
            // 删除用户数据
            var deleteUser = connection.prepareStatement(
                    "DELETE FROM `user` WHERE `id` = ?"
            );
            deleteUser.setLong(1, userId);
            deleteUser.execute();
            deleteUser.close();

            // 删除用户对应的角色数据
            var deleteUserRole = connection.prepareStatement(
                    "DELETE FROM user_role WHERE user_id = ?"
            );
            deleteUserRole.setLong(1, userId);
            deleteUserRole.execute();
            deleteUserRole.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加新用户
     */
    public static void addUser(String username, String password) {
        try {
            Class.forName(DATABASE.DRIVER);
            var connection = DriverManager.getConnection(
                    DATABASE.URL,
                    DATABASE.USERNAME,
                    DATABASE.PASSWORD
            );
            var insertStatement = connection.prepareStatement(
                    "INSERT INTO user (username, password) VALUES (?, ?)"
            );
            insertStatement.setString(1, username);
            insertStatement.setString(2, password);
            insertStatement.execute();
            insertStatement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
