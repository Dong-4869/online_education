package com.chen.serviceoss.controller;

import com.chen.commonutils.R;
import com.chen.serviceoss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags="阿里云文件管理")
//@CrossOrigin //跨域
@RestController
@RequestMapping("/eduoss/file")
public class FileUploadController {

    @Autowired
    private OssService ossService;

    @ApiOperation(value = "文件上传")
    @PostMapping("/upload")
    public R upload(@ApiParam(name = "file", value = "文件", required = true)
                    @RequestParam("file") MultipartFile file){
        String uploadUrl = ossService.upload(file);
        return R.ok().message("文件上传成功").data("url",uploadUrl);
    }

}
