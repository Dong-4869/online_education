package com.chen.orderservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.commonutils.JwtUtils;
import com.chen.commonutils.R;
import com.chen.orderservice.entity.TOrder;
import com.chen.orderservice.service.TOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author chen
 * @since 2021-08-14
 */
@RestController
@RequestMapping("/orderservice/order")
//@CrossOrigin
public class TOrderController {
    @Autowired
    private TOrderService orderService;


    //根据课程id和用户id创建订单，返回订单id
    @PostMapping("createOrder/{courseId}")
    public R save(@PathVariable("courseId") String courseId, HttpServletRequest request) {
        String orderId = orderService.saveOrder(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId", orderId);
    }

    @GetMapping("getOrder/{orderId}")
    public R get(@PathVariable String orderId) {
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        TOrder order = orderService.getOne(wrapper);
        return R.ok().data("item", order);
    }


    @GetMapping("isBuyCourse/{memberid}/{id}")
    public boolean isBuyCourse(@PathVariable("memberid") String memberid,
                               @PathVariable("id") String id) {
        //订单状态是1表示支付成功
        int count = orderService.count(new QueryWrapper<TOrder>()
                                        .eq("member_id", memberid)
                                        .eq("course_id", id)
                                        .eq("status", 1));
        if(count>0) {
            return true;
        } else {
            return false;
        }
    }
}

