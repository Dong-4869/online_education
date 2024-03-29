package com.chen.orderservice.service;

import com.chen.orderservice.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author chen
 * @since 2021-08-14
 */
public interface TOrderService extends IService<TOrder> {

    String saveOrder(String courseId, String memberId);
}
