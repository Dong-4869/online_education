package com.chen.servicevod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.chen.commonutils.R;
import com.chen.servicevod.service.VideoService;
import com.chen.servicevod.util.AliyunVodSDKUtils;
import com.chen.servicevod.util.ConstantPropertiesUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags="阿里云视频点播微服务")
//@CrossOrigin //跨域
@RestController
@RequestMapping("/vodservice/video")
public class VideoAdminController {
    @Autowired
    private VideoService videoService;

    @ApiOperation(value = "上传视频")
    @PostMapping("upload")
    public R uploadVideo(
                    @ApiParam(name = "file", value = "文件", required = true)
                    @RequestParam("file") MultipartFile file) throws Exception {
        String videoId = videoService.uploadVideo(file);
        return R.ok().message("视频上传成功").data("videoId", videoId);
    }



    @ApiOperation(value = "根据视频ID删除视频")
    @DeleteMapping("{videoId}")
    public R removeVideo(@ApiParam(name = "videoId", value = "云端视频id", required = true)
        @PathVariable String videoId){
        videoService.removeVideo(videoId);
        return R.ok().message("视频删除成功");
    }


    @ApiOperation(value = "批量删除视频")
    @DeleteMapping("delete-batch")
    public R removeVideoList(
                    @ApiParam(name = "videoIdList", value = "云端视频id", required = true)
                    @RequestParam("videoIdList") List<String> videoIdList){
        videoService.removeVideoList(videoIdList);
        return R.ok().message("视频删除成功");
    }


    @ApiOperation(value = "根据视频ID获得播放凭证")
    @GetMapping("get-play-auth/{videoId}")
    public R getVideoPlayAuth(@PathVariable("videoId") String videoId) throws Exception {
        //获取阿里云存储相关常量
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        //初始化
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(accessKeyId, accessKeySecret);
        //请求
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(videoId);
        //响应
        GetVideoPlayAuthResponse response = client.getAcsResponse(request);
        //得到播放凭证
        String playAuth = response.getPlayAuth();
        //返回结果
        return R.ok().message("获取凭证成功").data("playAuth", playAuth);
    }


}
