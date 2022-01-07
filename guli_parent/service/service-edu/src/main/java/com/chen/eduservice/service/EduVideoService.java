package com.chen.eduservice.service;

import com.chen.commonutils.R;
import com.chen.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author chen
 * @since 2021-08-06
 */
public interface EduVideoService extends IService<EduVideo> {

    boolean getCountByChapterId(String chapterId);

    boolean removeByCourseId(String courseId);

    R removeVideoById(String id);
}
