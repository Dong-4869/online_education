package com.chen.eduservice.service;

import com.chen.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author chen
 * @since 2021-08-05
 */
public interface EduSubjectService extends IService<EduSubject> {

    void importSubjectData(MultipartFile file);

    List<OneSubject> nestedList();
}
