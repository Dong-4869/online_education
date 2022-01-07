package com.chen.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.commonutils.JwtUtils;
import com.chen.commonutils.R;
import com.chen.eduservice.client.OrderClient;
import com.chen.eduservice.entity.EduCourse;
import com.chen.eduservice.entity.chapter.ChapterVo;
import com.chen.eduservice.entity.vo.front.CourseQueryVo;
import com.chen.commonutils.vo.CourseWebVo;
import com.chen.eduservice.service.EduChapterService;
import com.chen.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api(tags = "课程前端管理")
@RestController
@RequestMapping("/eduservice/course-front")
//@CrossOrigin
public class CourseFrontController {


    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrderClient orderClient;

    @ApiOperation(value = "分页课程列表")
    @PostMapping(value = "{page}/{limit}")
    public R pageList(
                @ApiParam(name = "page", value = "当前页码", required = true)
                @PathVariable Long page,
                @ApiParam(name = "limit", value = "每页记录数", required = true)
                @PathVariable Long limit,
                @ApiParam(name = "courseQuery", value = "查询对象", required = false)
        @RequestBody(required = false) CourseQueryVo courseQuery){
        Page<EduCourse> pageParam = new Page<EduCourse>(page, limit);
        Map<String, Object> map = courseService.pageListWeb(pageParam, courseQuery);
        return  R.ok().data(map);
    }


    @ApiOperation(value = "根据ID查询课程")
    @GetMapping(value = "{courseId}")
    public R getById(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId,
            HttpServletRequest request){
        //查询课程信息和讲师信息
        CourseWebVo courseWebVo = courseService.selectInfoWebById(courseId);
        //查询当前课程的章节信息
        List<ChapterVo> chapterVoList = chapterService.nestedList(courseId);

        //远程调用，判断课程是否被购买
        boolean buyCourse = orderClient.isBuyCourse(JwtUtils.getMemberIdByJwtToken(request), courseId);

        return R.ok().data("course", courseWebVo).data("chapterVoList", chapterVoList).data("isbuy",buyCourse);
    }
}
