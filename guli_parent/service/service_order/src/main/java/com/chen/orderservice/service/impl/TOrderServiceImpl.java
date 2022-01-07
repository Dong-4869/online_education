package com.chen.orderservice.service.impl;

import com.chen.commonutils.vo.CourseWebVo;
import com.chen.commonutils.vo.UcenterMember;
import com.chen.orderservice.client.EduClient;
import com.chen.orderservice.client.UcenterClient;
import com.chen.orderservice.entity.TOrder;
import com.chen.orderservice.mapper.TOrderMapper;
import com.chen.orderservice.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.orderservice.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author chen
 * @since 2021-08-14
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    @Autowired
    private EduClient eduClient;
    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public String saveOrder(String courseId, String memberId) {

        //远程调用用户服务，根据用户id获取用户信息
        UcenterMember ucenterMember = ucenterClient.getInfo(memberId);

        //远程调用课程服务，根据课程id获取课程信息
        CourseWebVo courseDto = eduClient.getCourseInfoDto(courseId);

        //创建订单
        TOrder order = new TOrder();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseDto.getTitle());
        order.setCourseCover(courseDto.getCover());
        order.setTeacherName(courseDto.getTeacherName());
        order.setTotalFee(courseDto.getPrice());
        order.setMemberId(memberId);
        order.setMobile(ucenterMember.getMobile());
        order.setNickname(ucenterMember.getNickname());
        order.setStatus(0);
        order.setPayType(1);
        baseMapper.insert(order);

        return order.getOrderNo();
    }
}
