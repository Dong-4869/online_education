package com.chen.eduservice.client.impl;

import com.chen.commonutils.vo.UcenterMember;
import com.chen.eduservice.client.UcenterClient;
import com.chen.servicebase.execption.GuliException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
public class UcenterClientImpl implements UcenterClient {

    @Override
    public UcenterMember getInfo(@PathVariable("id") String id){
        throw new GuliException(20001,"error");
    }
}
