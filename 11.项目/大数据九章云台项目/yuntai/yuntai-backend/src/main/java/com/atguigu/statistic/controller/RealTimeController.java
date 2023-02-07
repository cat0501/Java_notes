package com.atguigu.statistic.controller;

import com.atguigu.statistic.bean.GeneralStatistic;
import com.atguigu.statistic.service.StatisticService;
import com.atguigu.util.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/statistic/realtime")
public class RealTimeController {
    @GetMapping("gmv")
    public Result<BigDecimal> getGMV(@RequestParam String date) {
        return Result.of(200, "success", StatisticService.getGMV(date));
    }

    @GetMapping("province")
    public Result<List<GeneralStatistic>> getProvinceStatistic(@RequestParam String date) {
        return Result.of(200, "success", StatisticService.getProvinceStatistics(date));
    }

    @GetMapping("keyword")
    public Result<List<GeneralStatistic>> getKeywordStatistic(@RequestParam String date) {
        return Result.of(200, "success", StatisticService.getKeywordStatistics(date));
    }

    /**
     * 统计某天不同类别商品交易额排名
     */
    @GetMapping("category3")
    public Result<List<GeneralStatistic>> getProductStatsGroupByCategory3(@RequestParam String date) {
        return Result.of(200, "success", StatisticService.getProductStatisticsGroupByCategory3(date));
    }

    /**
     * 统计某天不同品牌商品交易额排名
     */
    @GetMapping("trademark")
    public Result<List<GeneralStatistic>> getProductStatsByTrademark(@RequestParam String date) {
        return Result.of(200, "success", StatisticService.getProductStatsByTrademark(date));
    }
}
