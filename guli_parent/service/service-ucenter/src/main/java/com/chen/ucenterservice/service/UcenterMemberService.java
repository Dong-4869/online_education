package com.chen.ucenterservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.ucenterservice.entity.UcenterMember;
import com.chen.ucenterservice.entity.vo.LoginInfoVo;
import com.chen.ucenterservice.entity.vo.LoginVo;
import com.chen.ucenterservice.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author chen
 * @since 2021-08-11
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(LoginVo loginVo);

    void register(RegisterVo registerVo);

    LoginInfoVo getLoginInfo(String memberId);

    UcenterMember getByOpenid(String openid);

    Integer countRegisterByDay(String day);
}
