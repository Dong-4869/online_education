package com.chen.eduservice.service;

import com.chen.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author chen
 * @since 2021-08-06
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> nestedList(String courseId);

    boolean removeChapterById(String id);

    boolean removeByCourseId(String courseId);
}
