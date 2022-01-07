package com.chen.orderservice.client.impl;

import com.chen.commonutils.vo.CourseWebVo;
import com.chen.orderservice.client.EduClient;
import com.chen.servicebase.execption.GuliException;
import org.springframework.stereotype.Component;

@Component
public class EduClientImpl implements EduClient {
    @Override
    public CourseWebVo getCourseInfoDto(String courseId) {
        throw new GuliException(20001,"error");
    }
}
