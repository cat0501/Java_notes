import request from '@/utils/request'

export default {
  executeCreateClickhouseTable(createTableString) {
    return request({
      url: '/report/createClickHouseTable',
      method: 'post',
      data: { sql: createTableString }
    })
  },
  executeCreateMySQLTable(createTableString) {
    return request({
      url: '/report/createMySQLTable',
      method: 'post',
      data: createTableString
    })
  }
}
