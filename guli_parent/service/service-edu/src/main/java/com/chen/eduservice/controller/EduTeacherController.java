package com.chen.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.commonutils.R;
import com.chen.eduservice.entity.EduTeacher;
import com.chen.eduservice.entity.vo.TeacherQuery;
import com.chen.eduservice.service.EduTeacherService;
import com.chen.servicebase.execption.GuliException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author chen
 * @since 2021-08-01
 */
@Api(tags = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
//@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/findAll")
    public R findAllTeacher() {
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public R removeById(@ApiParam(name = "id", value = "讲师ID", required = true)
                        @PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }

    }

    @ApiOperation(value = "分页讲师列表")
    @GetMapping("{page}/{limit}")
    public R pageList(@ApiParam(name = "page", value = "当前页码", required = true)
                      @PathVariable Long page,
                      @ApiParam(name = "limit", value = "每页记录数", required = true)
                      @PathVariable Long limit) {
        Page<EduTeacher> pageParam = new Page<>(page, limit);
        teacherService.page(pageParam, null);
        List<EduTeacher> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "分页讲师列表")
    @PostMapping("{page}/{limit}")
    public R pageQuery(@ApiParam(name = "page", value = "当前页码", required = true)
                       @PathVariable Long page,
                       @ApiParam(name = "limit", value = "每页记录数", required = true)
                       @PathVariable Long limit,
                       @ApiParam(name = "teacherQuery", value = "查询对象", required = false)
                       @RequestBody(required = false) TeacherQuery teacherQuery) {
        Page<EduTeacher> pageParam = new Page<>(page, limit);
        teacherService.pageQuery(pageParam, teacherQuery);
        List<EduTeacher> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "新增讲师")
    @PostMapping
    public R save(@ApiParam(name = "teacher", value = "讲师对象", required = true)
                  @RequestBody EduTeacher teacher) {
        boolean flag = teacherService.save(teacher);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("{id}")
    public R getById(@ApiParam(name = "id", value = "讲师ID", required = true)
                     @PathVariable String id) {
        EduTeacher teacher = teacherService.getById(id);
        return R.ok().data("item", teacher);
    }

    @ApiOperation(value = "根据ID修改讲师")
    @PutMapping()
    public R updateById(@ApiParam(name = "teacher", value = "讲师对象", required = true)
                        @RequestBody EduTeacher teacher) {
        boolean flag = teacherService.updateById(teacher);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

