package com.chen.eduservice.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.chen.commonutils.R;
import com.chen.eduservice.entity.EduVideo;
import com.chen.eduservice.handler.CustomerBlockHandler;
import com.chen.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author chen
 * @since 2021-08-06
 */

@Api(tags="课时管理")
//@CrossOrigin //跨域
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @ApiOperation(value = "新增课时")
    @PostMapping("save-video-info")
    public R save(
            @ApiParam(name = "EduVideo", value = "课时对象", required = true)
            @RequestBody EduVideo video){
        videoService.save(video);
        return R.ok();
    }

    @ApiOperation(value = "根据ID查询课时")
    @GetMapping("video-info/{id}")
    public R getVideInfoById(
                    @ApiParam(name = "id", value = "课时ID", required = true)
                    @PathVariable String id){
        EduVideo videoInfoForm = videoService.getById(id);
        return R.ok().data("item", videoInfoForm);
    }
    @ApiOperation(value = "更新课时")
    @PutMapping("update-video-info")
    public R updateCourseInfoById(
                    @ApiParam(name = "VideoInfoForm", value = "课时基本信息", required = true)
                    @RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo);
        return R.ok();
    }

    @SentinelResource(value = "customerBlockHandler",
            blockHandlerClass = CustomerBlockHandler.class, blockHandler = "handlerException2")
    @ApiOperation(value = "删除课时")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "课时ID", required = true)
            @PathVariable String id){
        R r = videoService.removeVideoById(id);
        return r;
    }
    
}

