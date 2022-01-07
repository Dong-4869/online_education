package com.chen.staservice.controller;


import com.chen.commonutils.R;
import com.chen.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author chen
 * @since 2021-08-16
 */
@RestController
@RequestMapping("/staservice/statistics")
//@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService dailyService;


    @PostMapping("{day}")
    public R createStatisticsByDate(@PathVariable String day) {
        dailyService.createStatisticsByDay(day);
        return R.ok();
    }

    @GetMapping("show-chart/{begin}/{end}/{type}")
    public R showChart(@PathVariable String begin,@PathVariable String end,@PathVariable String type){
        Map<String, Object> map = dailyService.getChartData(begin, end, type);
        return R.ok().data(map);
    }

}

