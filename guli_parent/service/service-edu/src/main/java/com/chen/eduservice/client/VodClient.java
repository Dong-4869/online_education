package com.chen.eduservice.client;

import com.chen.commonutils.R;
import com.chen.eduservice.client.impl.VodClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(value = "service-vod",fallback = VodClientImpl.class)
public interface VodClient {

    @DeleteMapping("/vodservice/video/{videoId}")
    public R removeVideo(@PathVariable("videoId") String videoId);

    @DeleteMapping("/vodservice/video/delete-batch")
    public R removeVideoList(@RequestParam("videoIdList") List<String> videoIdList);


}
