package com.chen.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.eduservice.entity.vo.TeacherQuery;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author chen
 * @since 2021-08-01
 */
public interface EduTeacherService extends IService<EduTeacher> {

    void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery);

    Map<String, Object> pageListWeb(Page<EduTeacher> pageParam);
}
