package com.chen.serviceoss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.chen.serviceoss.service.OssService;
import com.chen.serviceoss.util.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Component
public class OssServiceImpl implements OssService {
    @Override
    public String upload(MultipartFile file) {

        //获取阿里云存储相关常量
        String endPoint = ConstantPropertiesUtil.END_POINT;
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;



        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);

            //构建日期路径：avatar/yyyy/MM/dd/文件名
            String filePath = new DateTime().toString("yyyy/MM/dd");

            //获取上传文件流
            InputStream inputStream = file.getInputStream();

            //文件名：uuid.扩展名
            String original = file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String fileType = original.substring(original.lastIndexOf("."));
            String newName = fileName + fileType;
            String fileHost = "avatar";
            String fileUrl = fileHost + "/" + filePath + "/" + newName;

            //文件上传至阿里云
            ossClient.putObject(bucketName, fileUrl, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            String url = "https://" + bucketName + "." + endPoint + "/" + fileUrl;
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return null;


    }
}
