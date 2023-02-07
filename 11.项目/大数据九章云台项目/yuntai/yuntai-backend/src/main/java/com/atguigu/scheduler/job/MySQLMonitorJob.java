package com.atguigu.scheduler.job;

import com.atguigu.scheduler.bean.MonitorDetail;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLMonitorJob implements Job {
    public MySQLMonitorJob() {}

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        var dataMap = jobExecutionContext.getJobDetail().getJobDataMap();

        var databaseName = dataMap.getString("databaseName");
        var tableName = dataMap.getString("tableName");
        var fieldName = dataMap.getString("fieldName");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            var connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + databaseName + "?useSSL=false",
                    "root",
                    "root"
            );

            var monitorDetail = new MonitorDetail();
            monitorDetail.setDatabaseName(databaseName);
            monitorDetail.setTableName(tableName);
            monitorDetail.setFieldName(fieldName);

            var selectNullRate = connection.prepareStatement(
                    "SELECT" +
                            "   count(1) as TotalAll," +
                            "   count(" + fieldName + ") as TotalNotNull," +
                            "   count(1) - count(" + fieldName + ") as TotalNull," +
                            "   100.0 * count(" + fieldName + ") / count(1) as PercentNotNull" +
                            "  FROM " + tableName
            );
            var resultSet = selectNullRate.executeQuery();
            if (resultSet.next()) {
                monitorDetail.setFieldNullRate(resultSet.getDouble("PercentNotNull"));
            }

            var insertConnection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/yuntai_government?useSSL=false",
                    "root",
                    "root"
            );

            var insertStatement = insertConnection.prepareStatement(
                    "INSERT INTO mysql_data_monitor (database_name, table_name, field_name, field_null_rate) VALUES (?, ?, ?, ?)"
            );

            insertStatement.setString(1, databaseName);
            insertStatement.setString(2, tableName);
            insertStatement.setString(3, fieldName);
            insertStatement.setDouble(4, monitorDetail.getFieldNullRate());
            insertStatement.execute();

            selectNullRate.close();
            insertStatement.close();
            insertConnection.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
