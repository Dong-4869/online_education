package com.chen.eduservice.controller;


import com.chen.commonutils.R;
import com.chen.eduservice.entity.EduSubject;
import com.chen.eduservice.entity.subject.OneSubject;
import com.chen.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author chen
 * @since 2021-08-05
 */
@Api(tags="课程分类管理")
//@CrossOrigin
@RestController
@RequestMapping("/eduservice/subject")
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    //添加课程分类
    @ApiOperation(value = "Excel批量导入")
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        //1 获取上传的excel文件 MultipartFile
        //返回错误提示信息
        subjectService.importSubjectData(file);
        //判断返回集合是否为空
        return R.ok();
    }

    @ApiOperation(value = "嵌套数据列表")
    @GetMapping("")
    public R nestedList(){
        List<OneSubject> subjectNestedVoList = subjectService.nestedList();
        return R.ok().data("items", subjectNestedVoList);
    }

}

