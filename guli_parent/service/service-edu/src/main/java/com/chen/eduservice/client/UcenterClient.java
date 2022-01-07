package com.chen.eduservice.client;

import com.chen.commonutils.vo.UcenterMember;
import com.chen.eduservice.client.impl.UcenterClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Component
@FeignClient(value = "service-ucenter",fallback = UcenterClientImpl.class)
public interface UcenterClient {

    @GetMapping("/ucenterservice/apimember/getInfoUc/{id}")
    public UcenterMember getInfo(@PathVariable("id") String id);
}
