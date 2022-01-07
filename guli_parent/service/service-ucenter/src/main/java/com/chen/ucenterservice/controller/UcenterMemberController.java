package com.chen.ucenterservice.controller;


import com.chen.commonutils.JwtUtils;
import com.chen.commonutils.R;
import com.chen.ucenterservice.entity.UcenterMember;
import com.chen.servicebase.execption.GuliException;
import com.chen.ucenterservice.entity.vo.LoginInfoVo;
import com.chen.ucenterservice.entity.vo.LoginVo;
import com.chen.ucenterservice.entity.vo.RegisterVo;
import com.chen.ucenterservice.service.UcenterMemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author chen
 * @since 2021-08-11
 */
@RestController
@RequestMapping("/ucenterservice/apimember")
//@CrossOrigin
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService memberService;

    @ApiOperation(value = "会员登录")
    @PostMapping("login")
    public R login(@RequestBody LoginVo loginVo) {
        String token = memberService.login(loginVo);
        return R.ok().data("token", token);
    }
    @ApiOperation(value = "会员注册")
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }

    @ApiOperation(value = "根据token获取登录信息")
    @GetMapping("auth/getLoginInfo")
    public R getLoginInfo(HttpServletRequest request){
        try {
            String memberId = JwtUtils.getMemberIdByJwtToken(request);
            LoginInfoVo loginInfoVo = memberService.getLoginInfo(memberId);
            return R.ok().data("item", loginInfoVo);
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"error");
        }
    }

    @ApiOperation(value = "根据token获取评论需求信息")
    @GetMapping("getInfoUc/{id}")
    public com.chen.commonutils.vo.UcenterMember getInfo(@PathVariable("id") String id){
        UcenterMember ucenterMember = memberService.getById(id);
        com.chen.commonutils.vo.UcenterMember ucenterMember2 = new com.chen.commonutils.vo.UcenterMember();
        BeanUtils.copyProperties(ucenterMember,ucenterMember2);
        return ucenterMember2;
    }

    @GetMapping(value = "countregister/{day}")
    public R registerCount(@PathVariable("day") String day){
        Integer count = memberService.countRegisterByDay(day);
        return R.ok().data("countRegister", count);
    }
}

