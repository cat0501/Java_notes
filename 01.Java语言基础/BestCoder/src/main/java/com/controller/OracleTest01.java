package com.controller;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OracleTest01 {
    public static void main(String[] args) throws ClassNotFoundException {
        String str = "141,140";
        String[] strArray = str.split(",");
        List<Integer> integerList = Arrays.stream(strArray)
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        System.out.println(integerList.size());
        for (Integer s : integerList) {
            System.out.println(s);
        }

        Class.forName("oracle.jdbc.driver.OracleDriver");

        String url = "jdbc:oracle:thin:@10.11.3.33:1521:evmsc"; // 数据库连接 URL
        String username = "ev"; // 数据库用户名
        String password = "ev"; // 数据库密码
        String schema = "CSPT"; // 模式名
        String tableName = "SYS_VEH_MODEL"; // 表名

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet rs = databaseMetaData.getColumns(null, schema, tableName, "%");

            int count = 0;
            while (rs.next()) {
                count += 1;
                String columnName = rs.getString("COLUMN_NAME");
                String dataType = rs.getString("DATA_TYPE");
                int columnSize = rs.getInt("COLUMN_SIZE");
                // 其他字段信息...

                System.out.println("Column Name: " + columnName);
                System.out.println("Data Type: " + dataType);
                System.out.println("Column Size: " + columnSize);
                // 打印其他字段信息...
                System.out.println("----------");
            }

            System.out.println(count);
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
