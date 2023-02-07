import request from '@/utils/request'

export default {
  getHiveMetadataScore() {
    return request({
      url: `/government/hiveMetadataScore`,
      method: 'get'
    });
  },
  getMySQLDataMonitor() {
    return request({
      url: `/government/mysqlDataMonitor`,
      method: 'get'
    });
  },
  getLineage() {
    return request({
      url: `/government/lineage`,
      method: 'get'
    })
  },
}
