import request from '@/utils/request'

const api_name = '/eduservice/video'
export default {
    saveVideoInfo(videoInfo) {
        return request({
            url: `${api_name}/save-video-info`,
            method: 'post',
            data: videoInfo
        })
    },
    getVideoInfoById(id) {
        return request({
            url: `${api_name}/video-info/${id}`,
            method: 'get'
        })
    },
    updateVideoInfoById(videoInfo) {
        return request({
            url: `${api_name}/update-video-info`,
            method: 'put',
            data: videoInfo
        })
    },
    removeById(id) {
        return request({
            url: `${api_name}/${id}`,
            method: 'delete'
        })
    }
}
