import request from '@/utils/request'
export default {
  getList() {
    return request({
      url: `/educms/front-banner/getAllBanner`,
      method: 'get'
    })
  }
}