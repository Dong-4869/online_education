package com.chen.eduservice.mapper;

import com.chen.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.eduservice.entity.vo.CoursePublishVo;
import com.chen.commonutils.vo.CourseWebVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author chen
 * @since 2021-08-06
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVo getCoursePublishVoById(String id);

    CourseWebVo selectInfoWebById(String id);
}
