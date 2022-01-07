package com.chen.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "service-order")
public interface OrderClient {

    @GetMapping("/orderservice/order/isBuyCourse/{memberid}/{id}")
    public boolean isBuyCourse(@PathVariable("memberid") String memberid,@PathVariable("id") String id);
}
