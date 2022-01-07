package com.chen.eduservice.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.chen.commonutils.R;
import org.springframework.web.bind.annotation.PathVariable;

public class CustomerBlockHandler {

    public static R handlerException(BlockException exception) {
        return  R.error().data("message","sentinel控制台");
    }

    public static R handlerException2(@PathVariable String id, BlockException exception) {
        return  R.error().data("message","sentinel控制台");
    }
}
