import request from '@/utils/request'

const api_name = '/eduservice/course'
export default {

    saveCourseInfo(courseInfo) {
        return request({
            url: `${api_name}/save-course-info`,
            method: 'post',
            data: courseInfo
        })
    },
    getCourseInfoById(id) {
        return request({
            url: `${api_name}/course-info/${id}`,
            method: 'get'
        })
    },
    updateCourseInfoById(courseInfo) {
        return request({
            url: `${api_name}/update-course-info`,
            method: 'put',
            data: courseInfo
        })
    },
    getCoursePublishInfoById(id) {
        return request({
            url: `${api_name}/course-publish-info/${id}`,
            method: 'get'
        })
    },
    publishCourse(id) {
        return request({
            url: `${api_name}/publish-course/${id}`,
            method: 'put'
        })
    },
    getPageList(page, limit, searchObj) {
        return request({
            url: `${api_name}/${page}/${limit}`,
            method: 'post',
            data: searchObj
        })
    },
    removeById(id) {
        return request({
            url: `${api_name}/${id}`,
            method: 'delete'
        })
    }
}
