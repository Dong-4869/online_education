import request from '@/utils/request'

const api_name = '/eduservice/teacher'
export default {

    getTeacherListPage(current, limit, teacherQuery) {
        return request({
            url: `${api_name}/${current}/${limit}`,
            method: 'post',
            data: teacherQuery
        })
    },
    getList() {
        return request({
            url: `${api_name}/findAll`,
            method: 'get'
        })
    },
    removeById(teacherId) {
        return request({
            url: `${api_name}/${teacherId}`,
            method: 'delete'
        })
    },
    save(teacher) {
        return request({
            url: `${api_name}`,
            method: 'post',
            data: teacher
        })
    },
    getById(id) {
        return request({
            url: `${api_name}/${id}`,
            method: 'get'
        })
    },
    updateById(teacher) {
        return request({
            url: `${api_name}`,
            method: 'put',
            data: teacher
        })
    }

}


