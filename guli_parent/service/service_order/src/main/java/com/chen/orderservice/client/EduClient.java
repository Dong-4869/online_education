package com.chen.orderservice.client;

import com.chen.commonutils.vo.CourseWebVo;
import com.chen.orderservice.client.impl.EduClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "service-edu",fallback = EduClientImpl.class)
public interface EduClient {

    @GetMapping("/eduservice/course/getDto/{courseId}")
    public CourseWebVo getCourseInfoDto(@PathVariable("courseId") String courseId);
}
