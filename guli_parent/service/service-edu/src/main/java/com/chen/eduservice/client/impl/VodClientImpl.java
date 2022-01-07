package com.chen.eduservice.client.impl;

import com.chen.commonutils.R;
import com.chen.eduservice.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class VodClientImpl implements VodClient {
    @Override
    public R removeVideo(String videoId) {
        return R.error().message("删除单个视频出错！");
    }

    @Override
    public R removeVideoList(List<String> videoIdList) {
        return R.error().message("删除多个视频出错！");
    }
}
