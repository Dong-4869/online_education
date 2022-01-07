package com.chen.servicebase.handler;

import com.chen.commonutils.R;
import com.chen.servicebase.execption.GuliException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局异常处理。。。");
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行了自定义异常处理。。。");
    }

    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R error(GuliException e){
        e.printStackTrace();
        return R.error().message("执行了GuliException自定义异常处理。。。");
    }
}
