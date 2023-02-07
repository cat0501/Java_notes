package com.atguigu.report.service;

import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateTableService {
    public static String createMySQLTable(String sql) {
        try {
            var connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/gmall_report?useSSL=false",
                    "root",
                    "root"
            );
            var createTableStatement = connection.prepareStatement(sql);
            createTableStatement.execute();
            createTableStatement.close();
            connection.close();

            return "create table success";
        } catch (SQLException e) {
            e.printStackTrace();
            return "create table error";
        }
    }

    public static String createClickHouseTable(String sql) {
        try {
            var connection = DriverManager.getConnection(
                    "jdbc:clickhouse://localhost:8123/default?useSSL=false"
            );
            var createTableStatement = connection.prepareStatement(sql);
            createTableStatement.execute();
            createTableStatement.close();
            connection.close();

            return "create table success";
        } catch (SQLException e) {
            e.printStackTrace();
            return "create table error";
        }
    }
}
