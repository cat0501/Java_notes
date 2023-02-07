import request from '@/utils/request';

const api_name = '/statistic/visit';

export default {
  getTrafficStats(page, limit, searchObj) {
    return request({
      url: `${api_name}/getTrafficStats/${page}/${limit}`,
      method: 'get',
      params: searchObj
    })
  },
  getPagePathData(searchObj) {
    return request({
      url: `${api_name}/getPagePath`,
      method: 'get',
      params: searchObj
    })
  }
}
