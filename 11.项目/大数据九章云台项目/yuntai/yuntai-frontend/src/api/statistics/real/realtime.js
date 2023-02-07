import request from '@/utils/request';

const api_name = '/statistic/realtime';

export default {
  getGMV(today) {
    return request({
      url: `${api_name}/gmv?date=${today}`,
      method: 'get'
    })
  },
  getProductStatsGroupBySpu(today, limit) {
    return request({
      url: `${api_name}/spu?date=${today}&limit=${limit}`,
      method: 'get'
    })
  },
  getProductStatsGroupByCategory3(nowTimeStamp) {
    return request({
      url: `${api_name}/category3?date=${nowTimeStamp}`,
      method: 'get'
    })
  },
  getProductStatsByTrademark(today) {
    return request({
      url: `${api_name}/trademark?date=${today}`,
      method: 'get'
    })
  },
  getProvinceStats(today) {
    return request({
      url: `${api_name}/province?date=${today}`,
      method: 'get'
    })
  },
  getKeywordStats(today, limit) {
    return request({
      url: `${api_name}/keyword?date=${today}&limit=${limit}`,
      method: 'get'
    })
  },
}
