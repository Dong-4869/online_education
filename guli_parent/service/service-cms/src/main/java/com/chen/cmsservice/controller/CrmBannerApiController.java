package com.chen.cmsservice.controller;


import com.chen.cmsservice.entity.CrmBanner;
import com.chen.cmsservice.service.CrmBannerService;
import com.chen.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author chen
 * @since 2021-08-10
 */
@RestController
@RequestMapping("/educms/front-banner")
//@CrossOrigin
public class CrmBannerApiController {
    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation(value = "获取首页banner")
    @GetMapping("getAllBanner")
    public R index() {
        List<CrmBanner> list = bannerService.selectAllBanner();
        return R.ok().data("bannerList", list);
    }

}

