package com.chen.ucenterservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.commonutils.JwtUtils;
import com.chen.commonutils.MD5;
import com.chen.ucenterservice.entity.UcenterMember;
import com.chen.servicebase.execption.GuliException;
import com.chen.ucenterservice.entity.vo.LoginInfoVo;
import com.chen.ucenterservice.entity.vo.LoginVo;
import com.chen.ucenterservice.entity.vo.RegisterVo;
import com.chen.ucenterservice.mapper.UcenterMemberMapper;
import com.chen.ucenterservice.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author chen
 * @since 2021-08-11
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String login(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new GuliException(20001,"账号或密码不能为空");
        }

        UcenterMember member = baseMapper.selectOne(new QueryWrapper<UcenterMember>().eq("mobile", mobile));
        if(null == member){
            throw new GuliException(20001,"账号或密码错误");
        }

        if(!MD5.encrypt(password).equals(member.getPassword())){
            throw new GuliException(20001,"账号或密码错误");
        }

        if(member.getIsDisabled()){
            throw new GuliException(20001,"该账号被禁用!");
        }

        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return token;

    }

    @Override
    public void register(RegisterVo registerVo) {
        //获取注册信息，进行校验
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();
        //校验参数
        if(StringUtils.isEmpty(mobile) ||
        StringUtils.isEmpty(mobile) ||
        StringUtils.isEmpty(password) ||
        StringUtils.isEmpty(code)) {
            throw new GuliException(20001,"信息不能为空");
        }
        //校验校验验证码
        //从redis获取发送的验证码
        String mobleCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(mobleCode)) {
            throw new GuliException(20001,"验证码错误");
        }
        //查询数据库中是否存在相同的手机号码
        Integer count = baseMapper.selectCount(new QueryWrapper<UcenterMember>().eq("mobile", mobile));
        if(count.intValue() > 0) {
            throw new GuliException(20001,"该手机号码已被注册");
        }
        //添加注册信息到数据库
        UcenterMember member = new UcenterMember();
        member.setNickname(nickname);
        member.setMobile(registerVo.getMobile());
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("https://xm-edu.oss-cn-beijing.aliyuncs.com/avatar/2021/08/09/1e1886ea-d289-4107-a10d-fc0d4a4bae60.jpg");
        this.save(member);
    }

    @Override
    public LoginInfoVo getLoginInfo(String memberId) {
        UcenterMember member = baseMapper.selectById(memberId);
        LoginInfoVo loginInfoVo = new LoginInfoVo();
        BeanUtils.copyProperties(member, loginInfoVo);
        return loginInfoVo;
    }

    @Override
    public UcenterMember getByOpenid(String openid) {
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openid);
        UcenterMember member = baseMapper.selectOne(queryWrapper);
        return member;
    }

    @Override
    public Integer countRegisterByDay(String day) {
        return baseMapper.selectRegisterCount(day);
    }
}
