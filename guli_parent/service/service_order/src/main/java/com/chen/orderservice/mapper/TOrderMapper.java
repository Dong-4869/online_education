package com.chen.orderservice.mapper;

import com.chen.orderservice.entity.TOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 订单 Mapper 接口
 * </p>
 *
 * @author chen
 * @since 2021-08-14
 */

public interface TOrderMapper extends BaseMapper<TOrder> {

}
