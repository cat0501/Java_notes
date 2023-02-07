import request from '@/utils/request';

const api_name = '/schedule';

export default {
  getAllJobs() {
    return request({
      url: `${api_name}/getAllJobs`,
      method: 'get'
    })
  },
  deleteJob(id, jobName, jobGroup) {
    return request({
      url: `${api_name}/deleteJob`,
      method: 'post',
      data: { id, jobName, jobGroup }
    })
  },
  pauseJob(jobName, jobGroup) {
    return request({
      url: `${api_name}/pauseJob`,
      method: 'post',
      data: { jobName, jobGroup }
    })
  },
  resumeJob(jobName, jobGroup) {
    return request({
      url: `${api_name}/resumeJob`,
      method: 'post',
      data: { jobName, jobGroup }
    })
  },
  createJob(data, databaseName, tableName, fieldName) {
    return request({
      url: `${api_name}/createJob/${databaseName}/${tableName}/${fieldName}`,
      method: 'post',
      data: data
    })
  }
}
