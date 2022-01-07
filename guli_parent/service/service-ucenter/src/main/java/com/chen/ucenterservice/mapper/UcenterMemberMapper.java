package com.chen.ucenterservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.ucenterservice.entity.UcenterMember;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author chen
 * @since 2021-08-11
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer selectRegisterCount(String day);
}
