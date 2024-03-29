package com.chen.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.commonutils.R;
import com.chen.commonutils.vo.CourseWebVo;
import com.chen.eduservice.entity.EduCourse;
import com.chen.eduservice.entity.vo.CourseInfoForm;
import com.chen.eduservice.entity.vo.CoursePublishVo;
import com.chen.eduservice.entity.vo.CourseQuery;
import com.chen.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author chen
 * @since 2021-08-06
 */

@Api(tags="课程管理")
//@CrossOrigin //跨域
@RestController
@RequestMapping("/eduservice/course")
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;
    @ApiOperation(value = "新增课程")
    @PostMapping("save-course-info")
    public R saveCourseInfo(
                        @ApiParam(name = "CourseInfoForm", value = "课程基本信息", required = true)
                        @RequestBody CourseInfoForm courseInfoForm){
        String courseId = courseService.saveCourseInfo(courseInfoForm);
        if(!StringUtils.isEmpty(courseId)){
            return R.ok().data("courseId", courseId);
        }else{
            return R.error().message("保存失败");
        }
    }

    @ApiOperation(value = "根据ID查询课程")
    @GetMapping("course-info/{id}")
    public R getById(
                @ApiParam(name = "id", value = "课程ID", required = true)
                @PathVariable String id){
        CourseInfoForm courseInfoForm = courseService.getCourseInfoFormById(id);
        return R.ok().data("item", courseInfoForm);
    }



    @ApiOperation(value = "更新课程")
    @PutMapping("update-course-info")
    public R updateCourseInfoById(
                    @ApiParam(name = "CourseInfoForm", value = "课程基本信息", required = true)
                    @RequestBody CourseInfoForm courseInfoForm){
        courseService.updateCourseInfoById(courseInfoForm);
        return R.ok();
    }


    @ApiOperation(value = "根据ID获取课程发布信息")
    @GetMapping("course-publish-info/{id}")
    public R getCoursePublishVoById(
                @ApiParam(name = "id", value = "课程ID", required = true)
                @PathVariable String id){
        CoursePublishVo courseInfoForm = courseService.getCoursePublishVoById(id);
        return R.ok().data("item", courseInfoForm);
    }

    @ApiOperation(value = "根据id发布课程")
    @PutMapping("publish-course/{id}")
    public R publishCourseById(
                @ApiParam(name = "id", value = "课程ID", required = true)
                @PathVariable String id){
        courseService.publishCourseById(id);
        return R.ok();
    }

    @ApiOperation(value = "分页课程列表")
    @PostMapping("{page}/{limit}")
    public R pageQuery(
                    @ApiParam(name = "page", value = "当前页码", required = true)
                    @PathVariable Long page,
                    @ApiParam(name = "limit", value = "每页记录数", required = true)
                    @PathVariable Long limit,
                    @ApiParam(name = "courseQuery", value = "查询对象", required = false)
                    @RequestBody(required = false) CourseQuery courseQuery){
        Page<EduCourse> pageParam = new Page<>(page, limit);
        courseService.pageQuery(pageParam, courseQuery);
        List<EduCourse> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return  R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "根据ID删除课程")
    @DeleteMapping("{id}")
    public R removeById(
                @ApiParam(name = "id", value = "课程ID", required = true)
                @PathVariable String id){
        boolean result = courseService.removeCourseById(id);
        if(result){
            return R.ok();
        }else{
            return R.error().message("删除失败");
        }
    }

    //根据课程id查询课程信息
    @GetMapping("getDto/{courseId}")
    public CourseWebVo getCourseInfoDto(@PathVariable("courseId") String courseId) {
        CourseWebVo courseWebVoForm = courseService.selectInfoWebById(courseId);
        CourseWebVo courseWebVoTo = new CourseWebVo();
        BeanUtils.copyProperties(courseWebVoForm,courseWebVoTo);
        return courseWebVoTo;
    }
}

