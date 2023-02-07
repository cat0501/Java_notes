package com.atguigu.scheduler.controller;

import com.atguigu.scheduler.bean.MonitorDetail;
import com.atguigu.scheduler.bean.QuartzScheduler;
import com.atguigu.scheduler.bean.SchedulerJobInfo;
import com.atguigu.scheduler.job.MySQLMonitorJob;
import com.atguigu.scheduler.job.SimpleJob;
import com.atguigu.scheduler.service.SchedulerService;
import com.atguigu.util.Result;
import org.quartz.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@RestController
@RequestMapping("/schedule")
public class SchedulerController {
    /**
     * 获取所有作业
     */
    @GetMapping("getAllJobs")
    public Result<List<SchedulerJobInfo>> getAllJobs() {
        return Result.of(200, "success", SchedulerService.getAllJobs());
    }

    /**
     * 暂停作业
     */
    @PostMapping("pauseJob")
    public Result<String> pauseJob(@RequestBody SchedulerJobInfo jobInfo) throws Exception {
        var scheduler = QuartzScheduler.getInstance();
        scheduler.pauseJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
        jobInfo.setJobStatus("已暂停");
        SchedulerService.pauseJob(jobInfo);
        return Result.of(200, "success", "任务暂停");
    }

    /**
     * 重启作业
     */
    @PostMapping("resumeJob")
    public Result<String> resumeJob(@RequestBody SchedulerJobInfo jobInfo) throws Exception {
        var scheduler = QuartzScheduler.getInstance();
        scheduler.resumeJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
        jobInfo.setJobStatus("运行中");
        SchedulerService.resumeJob(jobInfo);
        return Result.of(200, "success", "任务重启");
    }

    /**
     * 删除作业
     */
    @PostMapping("deleteJob")
    public Result<String> deleteJob(@RequestBody SchedulerJobInfo jobInfo) throws Exception {
        var scheduler = QuartzScheduler.getInstance();
        scheduler.deleteJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
        SchedulerService.deleteJob(jobInfo);
        return Result.of(200, "success", "任务删除");
    }

    /**
     * 创建一个新作业
     */
    private static int triggerCount = 0;

    @PostMapping("createJob/{databaseName}/{tableName}/{fieldName}")
    public Result<String> createJob(@RequestBody SchedulerJobInfo jobInfo, @PathVariable String databaseName, @PathVariable String tableName, @PathVariable String fieldName) throws Exception {
        var scheduler = QuartzScheduler.getInstance();

        // 判断是什么任务，然后新建对应的任务
        JobDetail jobDetail;
        if (jobInfo.getJobType().equals("简单任务(测试)")) {
            jobDetail = JobBuilder.newJob(SimpleJob.class).withIdentity(jobInfo.getJobName(), jobInfo.getJobGroup()).build();
        } else {
            var jobData = new JobDataMap();
            jobData.put("databaseName", databaseName);
            jobData.put("tableName", tableName);
            jobData.put("fieldName", fieldName);
            jobDetail = JobBuilder
                    .newJob(MySQLMonitorJob.class)
                    .withIdentity(jobInfo.getJobName(), jobInfo.getJobGroup())
                    .setJobData(jobData)
                    .build();
        }

        // 新建触发器
        Trigger trigger;
        if (jobInfo.getCronJob()) {
            trigger = newTrigger()
                    .withIdentity("trigger" + triggerCount, jobInfo.getJobGroup())
                    .withSchedule(cronSchedule(jobInfo.getCronExpression()))
                    .build();
        } else {
            trigger = newTrigger()
                    .withIdentity("trigger" + triggerCount, jobInfo.getJobGroup())
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(jobInfo.getRepeatTime())
                            .repeatForever())
                    .build();
        }

        triggerCount++;

        // 调度任务
        scheduler.scheduleJob(jobDetail, trigger);

        // 将任务存入MySQL
        jobInfo.setJobStatus("运行中");
        SchedulerService.addJob(jobInfo);

        return Result.of(200, "success", "创建任务成功");
    }
}
