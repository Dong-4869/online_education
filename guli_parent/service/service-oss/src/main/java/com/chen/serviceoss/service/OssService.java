package com.chen.serviceoss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {

    /**
     * 文件上传至阿里云
     * @param file
     * @return
     */
    String upload(MultipartFile file);
}
