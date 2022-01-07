package com.chen.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.eduservice.entity.vo.CourseInfoForm;
import com.chen.eduservice.entity.vo.CoursePublishVo;
import com.chen.eduservice.entity.vo.CourseQuery;
import com.chen.eduservice.entity.vo.front.CourseQueryVo;
import com.chen.commonutils.vo.CourseWebVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author chen
 * @since 2021-08-06
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoForm courseInfoForm);

    CourseInfoForm getCourseInfoFormById(String id);

    void updateCourseInfoById(CourseInfoForm courseInfoForm);

    CoursePublishVo getCoursePublishVoById(String id);

    boolean publishCourseById(String id);

    void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery);

    boolean removeCourseById(String id);

    List<EduCourse> selectByTeacherId(String teacherId);

    Map<String, Object> pageListWeb(Page<EduCourse> pageParam, CourseQueryVo courseQuery);

    /**
     * 获取课程信息
     * @param id
     * @return
     */
        CourseWebVo selectInfoWebById(String id);
    /**
     * 更新课程浏览数
     * @param id
     */
    void updatePageViewCount(String id);

}
