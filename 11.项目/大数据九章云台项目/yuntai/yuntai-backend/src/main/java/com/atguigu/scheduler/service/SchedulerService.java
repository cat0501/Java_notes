package com.atguigu.scheduler.service;

import com.atguigu.scheduler.bean.SchedulerJobInfo;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SchedulerService {
    /**
     * 获取所有调度的任务
     */
    public static List<SchedulerJobInfo> getAllJobs() {
        try {
            var connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/yuntai_schedule?useSSL=false",
                    "root",
                    "root"
            );
            var selectStatement = connection.prepareStatement(
                    "SELECT * FROM scheduler_job_info"
            );
            var resultSet = selectStatement.executeQuery();
            var result = new ArrayList<SchedulerJobInfo>();
            while (resultSet.next()) {
                SchedulerJobInfo schedulerJobInfo = new SchedulerJobInfo();
                schedulerJobInfo.setId(resultSet.getLong("id"));
                schedulerJobInfo.setJobName(resultSet.getString("jobName"));
                schedulerJobInfo.setJobGroup(resultSet.getString("jobGroup"));
                schedulerJobInfo.setJobStatus(resultSet.getString("jobStatus"));
                schedulerJobInfo.setJobClass(resultSet.getString("jobClass"));
                schedulerJobInfo.setCronJob(resultSet.getBoolean("cronJob"));
                schedulerJobInfo.setCronExpression(resultSet.getString("cronExpression"));
                schedulerJobInfo.setRepeatTime(resultSet.getInt("repeatTime"));
                schedulerJobInfo.setJobType(resultSet.getString("jobType"));
                result.add(schedulerJobInfo);
            }

            selectStatement.close();
            connection.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 添加新的需要调度的任务
     */
    public static void addJob(SchedulerJobInfo jobInfo) {
        try {
            var connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/yuntai_schedule?useSSL=false&characterEncoding=utf8",
                    "root",
                    "root"
            );
            var insertStatement = connection.prepareStatement(
                    "INSERT INTO scheduler_job_info (jobName, jobGroup, jobStatus, repeatTime, jobClass, cronJob, cronExpression, jobType) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
            );
            insertStatement.setString(1, jobInfo.getJobName());
            insertStatement.setString(2, jobInfo.getJobGroup());
            insertStatement.setString(3, jobInfo.getJobStatus());
            insertStatement.setInt(4, jobInfo.getRepeatTime());
            insertStatement.setString(5, jobInfo.getJobClass());
            insertStatement.setBoolean(6, jobInfo.getCronJob());
            insertStatement.setString(7, jobInfo.getCronExpression());
            insertStatement.setString(8, jobInfo.getJobType());
            insertStatement.execute();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂停任务
     */
    public static void pauseJob(SchedulerJobInfo jobInfo) {
        try {
            var connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/yuntai_schedule?useSSL=false&characterEncoding=utf8",
                    "root",
                    "root"
            );
            var insertStatement = connection.prepareStatement(
                    "UPDATE scheduler_job_info SET jobStatus = '已暂停' WHERE jobName = ? AND jobGroup = ?"
            );
            insertStatement.setString(1, jobInfo.getJobName());
            insertStatement.setString(2, jobInfo.getJobGroup());
            insertStatement.execute();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 重启任务
     */
    public static void resumeJob(SchedulerJobInfo jobInfo) {
        try {
            var connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/yuntai_schedule?useSSL=false&characterEncoding=utf8",
                    "root",
                    "root"
            );
            var insertStatement = connection.prepareStatement(
                    "UPDATE scheduler_job_info SET jobStatus = '运行中' WHERE jobName = ? AND jobGroup = ?"
            );
            insertStatement.setString(1, jobInfo.getJobName());
            insertStatement.setString(2, jobInfo.getJobGroup());
            insertStatement.execute();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除某个任务
     */
    public static void deleteJob(SchedulerJobInfo jobInfo) {
        try {
            var connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/yuntai_schedule?useSSL=false",
                    "root",
                    "root"
            );
            var deleteStatement = connection.prepareStatement(
                    "DELETE FROM scheduler_job_info WHERE id = ?"
            );
            deleteStatement.setLong(1, jobInfo.getId());
            deleteStatement.execute();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
