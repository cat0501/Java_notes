package cn.itcast.order.controller;

import cn.itcast.entity.PageResult;
import cn.itcast.entity.Result;
import cn.itcast.entity.StatusCode;
import cn.itcast.model.TbOrder;
import cn.itcast.order.service.OrderService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author cat
 * @description
 * @date 2022/4/6 上午9:39
 */
@RestController
//@CrossOrigin(value = {"*"})
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping
    //@OperateLog
    public Result add(@RequestBody TbOrder order){
        //获取登录人名称
        orderService.add(order);
        return new Result(true, StatusCode.OK,"提交成功");
    }


    /***
     * 分页搜索实现
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    //@OperateLog
    public Result findPage(@RequestBody Map<String, Object> searchMap, @PathVariable  Integer page, @PathVariable  Integer size){
        Page<TbOrder> pageList = orderService.findPage(searchMap, page, size);

        PageResult pageResult=new PageResult(pageList.getTotal(),pageList.getResult());
        return new Result(true,StatusCode.OK,"查询成功",pageResult);
    }
}
