package com.chen.eduservice.controller;

import com.chen.commonutils.R;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/eduservice/user")
//@CrossOrigin //解决跨域问题
public class EduLoginController {

    @PostMapping("/login")
    public R login() {

        return R.ok().data("token", "admin");
    }

    @GetMapping("/info")
    public R info() {

        return R.ok().data("roles", "[admin]").data("name", "admin").data("avatar", "https://cdn3-banquan.ituchong.com/weili/l/747687079685521436.webp");
    }

}
