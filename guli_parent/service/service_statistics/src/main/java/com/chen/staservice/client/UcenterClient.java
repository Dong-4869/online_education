package com.chen.staservice.client;

import com.chen.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "service-ucenter")
public interface UcenterClient {

    @GetMapping(value = "/ucenterservice/apimember/countregister/{day}")
    public R registerCount(@PathVariable("day") String day);

}
