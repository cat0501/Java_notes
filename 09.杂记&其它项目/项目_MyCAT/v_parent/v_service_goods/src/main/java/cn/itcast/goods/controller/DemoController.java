package cn.itcast.goods.controller;

import cn.itcast.goods.aop.OperateLog;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cat
 * @description
 * @date 2022/4/5 下午10:57
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @RequestMapping("/show")
    @OperateLog
    public String show(){
        return "OK";
    }
}
