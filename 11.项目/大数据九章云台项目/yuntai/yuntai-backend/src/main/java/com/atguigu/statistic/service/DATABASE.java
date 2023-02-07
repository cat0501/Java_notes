package com.atguigu.statistic.service;

public class DATABASE {
    public static String MYSQL_URL = "jdbc:mysql://localhost:3306/gmall_report?useSSL=false";
    public static String MYSQL_USERNAME = "root";
    public static String MYSQL_PASSWORD = "root";
    public static String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";

    public static String CLICKHOUSE_URL = "jdbc:clickhouse://localhost:8123/default";
    public static String CLICKHOUSE_DRIVER = "com.clickhouse.jdbc.ClickHouseDriver";
}
