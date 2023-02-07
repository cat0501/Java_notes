<template>
  <div class="app-container">
    <el-card class="operate-container" shadow="never">
      <i class="el-icon-tickets" style="margin-top: 5px" />
      <span style="margin-top: 5px">任务列表</span>
      <el-button class="btn-add" size="mini" @click="addJob">添加任务</el-button>
    </el-card>

    <table class="tableCss">
      <tr style="text-align: center;">
        <th>任务ID</th>
        <th>任务类型</th>
        <th>任务名</th>
        <th>任务组</th>
        <th>是否是cron任务</th>
        <th>cron表达式</th>
        <th>执行周期</th>
        <th>任务状态</th>
        <th>操作</th>
      </tr>
      <tr v-for="item in allJobs" style="text-align: center;">
        <td>{{ item.id }}</td>
        <td>{{ item.jobType }}</td>
        <td>{{ item.jobName }}</td>
        <td>{{ item.jobGroup }}</td>
        <td>{{ item.cronJob }}</td>
        <td>{{ item.cronExpression }}</td>
        <td>{{ item.repeatTime }} 秒</td>
        <td>{{ item.jobStatus }}</td>
        <td>
          <button @click="deleteJob(item.id, item.jobName, item.jobGroup)">删除任务</button>
          <button v-if="item.jobStatus === '已暂停'" @click="resumeJob(item.jobName, item.jobGroup)">重启任务</button>
          <button v-if="item.jobStatus === '运行中'" @click="pauseJob(item.jobName, item.jobGroup)">暂停任务</button>
        </td>
      </tr>
    </table>

    <el-dialog title="创建新任务" :visible.sync="dialogVisible">
      <el-form label-width="80px">
        <el-form-item label="作业列表">
          <el-select v-model="jobType">
            <el-option :key="'SimpleJob'" :value="'简单任务(测试)'"></el-option>
            <el-option :key="'MySQLJob'" :value="'MySQL数据监控任务'"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="数据库名" v-if="jobType === 'MySQL数据监控任务'">
          <el-select v-model="databaseName">
            <el-option :key="'gmall'" :value="'gmall'"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="表名" v-if="jobType === 'MySQL数据监控任务'">
          <el-select v-model="tableName">
            <el-option :key="'table'" :value="'yuntai_zuoyuan'"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="字段名" v-if="jobType === 'MySQL数据监控任务'">
          <el-select v-model="fieldName">
            <el-option :key="'age'" :value="'age'"></el-option>
            <el-option :key="'height'" :value="'height'"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="任务" v-if="jobType === 'MySQL数据监控任务'">
          <el-select v-model="MySQLJobType">
            <el-option :key="'nullRate'" :value="'空值率'"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="任务类型">
          <el-select v-model="cronJob">
            <el-option :key="'SimpleTypeJob'" :value="'周期执行任务'"></el-option>
            <el-option :key="'CronTypeJob'" :value="'Cron任务'"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="执行周期" v-if="cronJob === '周期执行任务'">
          <el-select v-model="repeatTime">
            <el-option :key="'TenSeconds'" :value="'每10秒钟执行一次'"></el-option>
            <el-option :key="'OneMinute'" :value="'每分钟执行一次'"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="Cron表达式" v-if="cronJob === 'Cron任务'">
          <el-input v-model="cronExpression" />
        </el-form-item>
        <el-form-item label="作业名">
          <el-input v-model="jobName" />
        </el-form-item>
        <el-form-item label="组名">
          <el-input v-model="jobGroup" />
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button type="primary" size="small" @click="createJob">创建新任务</el-button>
        <el-button size="small" @click="dialogVisible = false">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import api from '@/api/schedule/schedule';

export default {
  data() {
    return {
      allJobs: [],
      dialogVisible: false,
      jobName: '',
      jobGroup: '',
      jobType: '简单任务(测试)',
      repeatTime: '每10秒钟执行一次',
      cronJob: '周期执行任务',
      cronExpression: '',
      databaseName: 'gmall',
      tableName: 'yuntai_zuoyuan',
      fieldName: 'age',
      MySQLJobType: '空值率',
    }
  },
  created() {
    this.fetchData();
  },
  methods: {
    // 加载banner列表数据
    fetchData() {
      api.getAllJobs().then(response => {
        this.allJobs = response.data;
      })
    },
    addJob() {
      this.dialogVisible = true;
    },
    deleteJob(id, jobName, jobGroup) {
      api.deleteJob(id, jobName, jobGroup).then(response => {
        this.fetchData();
      })
    },
    resumeJob(jobName, jobGroup) {
      api.resumeJob(jobName, jobGroup).then(response => {
        this.fetchData();
      })
    },
    pauseJob(jobName, jobGroup) {
      api.pauseJob(jobName, jobGroup).then(response => {
        this.fetchData();
      })
    },
    createJob() {
      const data = {
        jobName: this.jobName,
        jobType: this.jobType,
        jobGroup: this.jobGroup,
        cronJob: this.cronJob === '周期执行任务' ? false : true,
        cronExpression: this.cronExpression,
        repeatTime: this.repeatTime === '每10秒钟执行一次' ? 10 : 60,
      };

      api.createJob(data, this.databaseName, this.tableName, this.fieldName).then(response => {
        this.fetchData();
        this.dialogVisible = false;
      });
    }
  }
}
</script>

<style scoped>
.tableCss {
  width: 100%;
  margin-top: 10px;
  border: 1px solid #ebeef5;
}

table>tr>th {
  padding: 10px;
}

table>tr>td {
  padding: 10px;
}

button {
  margin: 10px;
}
</style>