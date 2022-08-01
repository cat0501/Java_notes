package com.zjl.litemall.admin.controller;

import com.zjl.litemall.core.util.ResponseUtil;
import com.zjl.litemall.db.service.GoodsProductService;
import com.zjl.litemall.db.service.GoodsService;
import com.zjl.litemall.db.service.OrderService;
import com.zjl.litemall.db.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cat
 * @description
 * @date 2022/4/5 上午11:42
 */
@RestController
@RequestMapping("/admin/dashboard")
public class DashbordController {

    @Autowired
    private UserService userService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsProductService productService;
    @Autowired
    private OrderService orderService;

    @GetMapping("")
    public Object info() {
        int userTotal = userService.count();
        int goodsTotal = goodsService.count();
        int productTotal = productService.count();
        int orderTotal = orderService.count();
        Map<String, Integer> data = new HashMap<>();
        data.put("userTotal", userTotal);
        data.put("goodsTotal", goodsTotal);
        data.put("productTotal", productTotal);
        data.put("orderTotal", orderTotal);

        return ResponseUtil.ok(data);
    }
}
